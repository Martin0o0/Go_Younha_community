package com.example.goyounhacom.web.Controller.MainPostsApiController;

import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.Service.*;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.Recomment;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.RecommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.FileDto.MainPostFileDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mainpost")
public class MainPostPageController {
    private final MainPostsService mainPostsService;
    private final UserService userService;
    private final PrincipalDetailService principalDetailService;
    private final FileService fileService;
    private final RecommentService recommentService;
    private final ScrapService scrapService;
    private final MainPostLikeService mainPostLikeService;


    @GetMapping("/mainlist")
    public String mainpost(@AuthenticationPrincipal PrincipalDatails principalDatails, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 5) Pageable pageable, @RequestParam(required = false, defaultValue = "") String search) {
        Page<MainPost> list = mainPostsService.search(search, search, pageable); //??????????????????.
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }
        model.addAttribute("main_post", list);
        model.addAttribute("search", search);
        return "MainPost";
    }

    @GetMapping("/comment/{id}")
    public String mainpostcoment(@PathVariable Long id, Model model, Long errorpoint, String iserror,CommentSaveDto commentSaveDto, RecommentSaveDto recommentSaveDto, @AuthenticationPrincipal PrincipalDatails principalDatails) {
        errorpoint = (Long) model.getAttribute("errorpoint");
        iserror = (String) model.getAttribute("iserror");

        long cnt = mainPostLikeService.getCountLike(id);
        model.addAttribute("countlike", cnt);


        MainPostGetDto post = mainPostsService.getMainpost(id);
        model.addAttribute("main_post", post);
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
            boolean haslike = mainPostLikeService.existLike(user.getId(), id); //?????????,
            log.info("????????? ?????? : {}", haslike);
            model.addAttribute("haslike", haslike);
            boolean hasScrap = scrapService.findByScrap(user.getId(), id);
            log.info("????????? ?????? : {}" , hasScrap);
            model.addAttribute("hasscrap", hasScrap);

        }

        if (post.getFileId() != null) {
            MainPostFileDto filedto = fileService.getFile(post.getFileId());
            model.addAttribute("filename", filedto.getOriginalFilename());
        }

        if (recommentService.existbymainpostid(id)) {
            List<Recomment> list = recommentService.findallbymainpostid(id);
            model.addAttribute("recomment", list);
        }
        if (errorpoint != null && iserror != null) {
            model.addAttribute("errorpoint", errorpoint);
            model.addAttribute("iserror", iserror);
        } else {
            model.addAttribute("errorpoint", null);
            model.addAttribute("iserror", null);
        }
        mainPostsService.updateviewcount(id);
        return "MainPost_comment";
    }

    @PreAuthorize("isAuthenticated()") //???????????? ?????? ???????????? ???????????? ????????? ???????????? ??????
    @GetMapping("/save")
    public String mainpostpage(MainPostSaveDto mainPostSaveDto, Model model, @AuthenticationPrincipal PrincipalDatails principalDatails) {
        model.addAttribute("posttype", "????????? ??????");

        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }
        model.addAttribute("fileid", null);
        return "MainPost_save";
    } //??????????????? ?????????.


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/save")
    public String mainpostsave(@RequestParam("file") MultipartFile files, @Valid MainPostSaveDto mainPostSaveDto, BindingResult bindingResult, Principal principal, Model model, @AuthenticationPrincipal PrincipalDatails principalDatails) { //BindingResult ??????????????? @Valid ????????????????????? ?????? ????????? ????????? ????????? ???????????? ????????????.
        model.addAttribute("posttype", "????????? ??????");
        model.addAttribute("fileid", null);
        log.info("?????? ?????? : {}", files.getContentType());
        User user = userService.getbyUsername(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("userinfo", user);

            return "MainPost_save";
        }
        try {
            Long fileId = null;
            if (files.isEmpty() == false) {
                String originalFileName = files.getOriginalFilename();
                log.info("?????? ?????? : {}", originalFileName);
                UUID uuid = UUID.randomUUID();
                String filename = uuid.toString() + "_" + originalFileName; //?????? ?????? ?????? ??????.
                log.info("????????? ?????? ?????? : {} ", filename);
                String savePath = System.getProperty("user.dir") + "/files";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + filename;
                log.info("?????? ?????? : {} ", filePath);
                files.transferTo(new File(filePath));

                MainPostFileDto fileDto = new MainPostFileDto();
                fileDto.setOriginalFilename(originalFileName);
                fileDto.setFilename(filename);
                fileDto.setFilePath(filePath);
                fileDto.setFiletype(files.getContentType());
                log.info(fileDto.toString());
                fileId = fileService.saveFile(fileDto);
            }
            mainPostSaveDto.setFileId(fileId);
            long savenum = mainPostsService.save(mainPostSaveDto.getTitle(), mainPostSaveDto.getContent(), fileId, user);
            log.info("????????? ??? ?????? : {}", savenum);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/mainpost/mainlist";

    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        MainPostFileDto fileDto = fileService.getFile(fileId);
        Path path = Paths.get(fileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDto.getOriginalFilename() + "\"")
                .body(resource); //inline = ????????? ??????, attachment = ????????? ??????.
    }


    @GetMapping("/display/{fileId}")
    public ResponseEntity<Resource> fileDisplay(@PathVariable Long fileId) throws IOException {
        MainPostFileDto fileDto = fileService.getFile(fileId);
        Path path = Paths.get(fileDto.getFilePath());
        log.info("{}", path);
        log.info("{}", path.toString());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .header("Content-type", Files.probeContentType(path))
                .body(resource); //????????????.

    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/put/{id}")
    public String mainpostput(@PathVariable Long id, Principal principal, MainPostSaveDto mainPostSaveDto, Model model, @AuthenticationPrincipal PrincipalDatails principalDatails) {
        model.addAttribute("posttype", "????????? ??????");
        MainPostGetDto Dto = mainPostsService.getMainpost(id);
        if (Dto.getUser().getUsername().equals(principal.getName()) == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "?????? ????????? ??????");
        }
        mainPostSaveDto.setTitle(Dto.getTitle());
        mainPostSaveDto.setContent(Dto.getContent());
        if (Dto.getFileId() != null) {
            mainPostSaveDto.setFileId(Dto.getFileId());
            model.addAttribute("fileid", Dto.getFileId());
        } else {
            model.addAttribute("fileid", null);
        }
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }
        return "MainPost_save";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/put/{id}")
    public String mainpostupdate(@RequestParam("file") MultipartFile files, @PathVariable Long id, @Valid MainPostSaveDto mainPostSaveDto, BindingResult bindingResult, Principal principal, Model model, @AuthenticationPrincipal PrincipalDatails principalDatails) { //BindingResult ??????????????? @Valid ????????????????????? ?????? ????????? ????????? ????????? ???????????? ????????????.
        MainPostGetDto Dto = mainPostsService.getMainpost(id);
        model.addAttribute("posttype", "????????? ??????");
        model.addAttribute("fileid", Dto.getFileId());
        if (bindingResult.hasErrors()) {
            if (principalDatails != null) {
                User user = userService.getbyUsername(principalDatails.getUsername());
                model.addAttribute("userinfo", user);
            }
            return "MainPost_save";
        }
        if (Dto.getUser().getUsername().equals(principal.getName()) == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "?????? ????????? ??????");
        }
        Long fileId = Dto.getFileId();
        try {
            if (files.isEmpty() == false) {
                String originalFileName = files.getOriginalFilename();
                log.info("?????? ?????? : {}", originalFileName);
                UUID uuid = UUID.randomUUID();
                String filename = uuid.toString() + "_" + originalFileName; //?????? ?????? ?????? ??????.
                log.info("????????? ?????? ?????? : {} ", filename);
                String savePath = System.getProperty("user.dir") + "/files";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + filename;
                log.info("?????? ?????? : {} ", filePath);
                files.transferTo(new File(filePath));

                MainPostFileDto fileDto = new MainPostFileDto();
                fileDto.setOriginalFilename(originalFileName);
                fileDto.setFilename(filename);
                fileDto.setFilePath(filePath);
                fileDto.setFiletype(files.getContentType());
                log.info(fileDto.toString());
                if (fileId != null) {
                    fileId = fileService.modify(fileId, fileDto);
                } else {
                    fileId = fileService.saveFile(fileDto);
                }

            }
            mainPostSaveDto.setFileId(fileId);

        } catch (Exception e) {
            e.printStackTrace();
        }


        long savenum = mainPostsService.modify(id, mainPostSaveDto.getTitle(), mainPostSaveDto.getContent(), fileId);
        log.info("????????? ??? ?????? : {}", savenum);
        return "redirect:/mainpost/comment/" + id;
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String mainpoostdelete(@PathVariable Long id, Principal principal) {
        MainPostGetDto Dto = mainPostsService.getMainpost(id);
        User user = userService.getbyUsername(principal.getName());
        log.info("{ROLE : {}, is true ? {} ", user.getRoleKey(), user.getRoleKey().contentEquals("ROLE_ADMIN"));

        if (user.getRoleKey().contentEquals("ROLE_ADMIN")) {
            long deletenum = mainPostsService.delete(id);
            log.info("????????? ??? ?????? : {}", deletenum);
            return "redirect:/";
        } else if (Dto.getUser().getUsername().equals(principal.getName()) == true) {
            long deletenum = mainPostsService.delete(id);
            log.info("????????? ??? ?????? : {}", deletenum);
            return "redirect:/";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "?????? ????????? ??????");
        }
    }

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/like/{id}")
//    public String mainpostlike(@PathVariable Long id, Principal principal) {
//        User user = this.userService.getbyUsername(principal.getName());
//        this.mainPostsService.like(id, user);
//        return "redirect:/mainpost/comment/" + id;
//    }

}
