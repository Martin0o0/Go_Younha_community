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

    @GetMapping("/mainlist")
    public String mainpost(@AuthenticationPrincipal PrincipalDatails principalDatails, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 5) Pageable pageable, @RequestParam(required = false, defaultValue = "") String search) {
        Page<MainPost> list = mainPostsService.search(search, search, pageable); //읽기전용으로.
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }
        model.addAttribute("main_post", list);
        model.addAttribute("search", search);
        return "MainPost";
    }

    @GetMapping("/comment/{id}")
    public String mainpostcoment(@PathVariable Long id, Model model, Long errorpoint, String iserror ,CommentSaveDto commentSaveDto, RecommentSaveDto recommentSaveDto ,@AuthenticationPrincipal PrincipalDatails principalDatails) {
        errorpoint = (Long)model.getAttribute("errorpoint");
        iserror = (String)model.getAttribute("iserror");
        MainPostGetDto post = mainPostsService.getMainpost(id);
        model.addAttribute("main_post", post);
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }

        if (post.getFileId() != null) {
            MainPostFileDto filedto = fileService.getFile(post.getFileId());
            model.addAttribute("filename", filedto.getOriginalFilename());
        }

        if(recommentService.existbymainpostid(id)){
            List<Recomment> list = recommentService.findallbymainpostid(id);
            model.addAttribute("recomment", list);
        }
        if(errorpoint != null && iserror != null){
            model.addAttribute("errorpoint", errorpoint);
            model.addAttribute("iserror", iserror);
        }
        else {
            model.addAttribute("errorpoint", null);
            model.addAttribute("iserror", null);
        }
        mainPostsService.updateviewcount(id);
        return "MainPost_comment";
    }

    @PreAuthorize("isAuthenticated()") //로그인이 붙은 메서드는 로그인이 필요한 메서드를 의미
    @GetMapping("/save")
    public String mainpostpage(MainPostSaveDto mainPostSaveDto, Model model) {
        model.addAttribute("posttype", "게시글 등록");
        model.addAttribute("fileid", null);
        return "MainPost_save";
    } //발리데이션 때문에.


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/save")
    public String mainpostsave(@RequestParam("file") MultipartFile files, @Valid MainPostSaveDto mainPostSaveDto, BindingResult bindingResult, Principal principal, Model model) { //BindingResult 매개변수는 @Valid 애너테이션으로 인해 검증이 수행된 결과를 의미하는 객체이다.
        model.addAttribute("posttype", "게시글 등록");
        model.addAttribute("fileid", null);
        log.info("파일 형식 : {}", files.getContentType());
        User user = userService.getbyUsername(principal.getName());
        if (bindingResult.hasErrors()) {
            return "MainPost_save";
        }
        try {
            Long fileId = null;
            if (files.isEmpty() == false) {
                String originalFileName = files.getOriginalFilename();
                log.info("파일 이름 : {}", originalFileName);
                UUID uuid = UUID.randomUUID();
                String filename = uuid.toString() + "_" + originalFileName; //파일 이름 중복 방지.
                log.info("변조된 파일 이름 : {} ", filename);
                String savePath = System.getProperty("user.dir") + "/files";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + filename;
                log.info("파일 경로 : {} ", filePath);
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
            log.info("저장된 글 번호 : {}", savenum);

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
                .body(resource); //inline = 이미지 표출, attachment = 이미지 저장.
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
                .body(resource); //표출해라.

    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/put/{id}")
    public String mainpostput(@PathVariable Long id, Principal principal, MainPostSaveDto mainPostSaveDto, Model model) {
        model.addAttribute("posttype", "게시글 수정");
        MainPostGetDto Dto = mainPostsService.getMainpost(id);
        if (Dto.getUser().getUsername().equals(principal.getName()) == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }
        mainPostSaveDto.setTitle(Dto.getTitle());
        mainPostSaveDto.setContent(Dto.getContent());
        if (Dto.getFileId() != null) {
            mainPostSaveDto.setFileId(Dto.getFileId());
            model.addAttribute("fileid", Dto.getFileId());
        } else {
            model.addAttribute("fileid", null);
        }
        return "MainPost_save";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/put/{id}")
    public String mainpostupdate(@RequestParam("file") MultipartFile files, @PathVariable Long id, @Valid MainPostSaveDto mainPostSaveDto, BindingResult bindingResult, Principal principal, Model model) { //BindingResult 매개변수는 @Valid 애너테이션으로 인해 검증이 수행된 결과를 의미하는 객체이다.
        MainPostGetDto Dto = mainPostsService.getMainpost(id);
        model.addAttribute("posttype", "게시글 수정");
        model.addAttribute("fileid", Dto.getFileId());
        if (bindingResult.hasErrors()) {
            return "MainPost_save";
        }
        if (Dto.getUser().getUsername().equals(principal.getName()) == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }
        Long fileId = Dto.getFileId();
        try {
            if (files.isEmpty() == false) {
                String originalFileName = files.getOriginalFilename();
                log.info("파일 이름 : {}", originalFileName);
                UUID uuid = UUID.randomUUID();
                String filename = uuid.toString() + "_" + originalFileName; //파일 이름 중복 방지.
                log.info("변조된 파일 이름 : {} ", filename);
                String savePath = System.getProperty("user.dir") + "/files";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + filename;
                log.info("파일 경로 : {} ", filePath);
                files.transferTo(new File(filePath));

                MainPostFileDto fileDto = new MainPostFileDto();
                fileDto.setOriginalFilename(originalFileName);
                fileDto.setFilename(filename);
                fileDto.setFilePath(filePath);
                fileDto.setFiletype(files.getContentType());
                log.info(fileDto.toString());
                if(fileId != null){
                    fileId = fileService.modify(fileId, fileDto);
                }
                else{
                    fileId = fileService.saveFile(fileDto);
                }

            }
            mainPostSaveDto.setFileId(fileId);

        } catch (Exception e) {
            e.printStackTrace();
        }


        long savenum = mainPostsService.modify(id, mainPostSaveDto.getTitle(), mainPostSaveDto.getContent(), fileId);
        log.info("저장된 글 번호 : {}", savenum);
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
            log.info("삭제된 글 번호 : {}", deletenum);
            return "redirect:/";
        } else if (Dto.getUser().getUsername().equals(principal.getName()) == true) {
            long deletenum = mainPostsService.delete(id);
            log.info("삭제된 글 번호 : {}", deletenum);
            return "redirect:/";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String mainpostlike(@PathVariable Long id, Principal principal) {
        User user = this.userService.getbyUsername(principal.getName());
        this.mainPostsService.like(id, user);
        return "redirect:/mainpost/comment/" + id;
    }

}
