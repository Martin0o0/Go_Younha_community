package com.example.goyounhacom.web.Controller.MainPostsApiController;

import com.example.goyounhacom.Service.MainPostsService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostSaveDto;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mainpost")
public class MainPostPageController {
    private final MainPostsService mainPostsService;
    private final UserService userService;

    @GetMapping("/mainlist")
    public String mainpost(Model model, @PageableDefault(sort="id", direction = Sort.Direction.DESC, size = 5) Pageable pageable){
        Page<MainPost> list = mainPostsService.pageList(pageable); //읽기전용으로.

        model.addAttribute("main_post", list);
        return "MainPost";
    }

    @GetMapping("/comment/{id}")
    public String mainpostcoment(@PathVariable Long id, Model model, CommentSaveDto commentSaveDto){
        MainPostGetDto post = mainPostsService.getMainpost(id);
        model.addAttribute("main_post", post);
        return "MainPost_comment";
    }



    @PreAuthorize("isAuthenticated()") //로그인이 붙은 메서드는 로그인이 필요한 메서드를 의미
    @GetMapping("/save")
    public String mainpostpage(MainPostSaveDto mainPostSaveDto){
        return "MainPost_save";
    } //발리데이션 때문에.


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/save")
    public String mainpostsave(@Valid MainPostSaveDto mainPostSaveDto, BindingResult bindingResult, Principal principal){ //BindingResult 매개변수는 @Valid 애너테이션으로 인해 검증이 수행된 결과를 의미하는 객체이다.
        User user = userService.getbyUsername(principal.getName());
        if(bindingResult.hasErrors()){
            return  "MainPost_save";
        }
        long savenum = mainPostsService.save(mainPostSaveDto.getTitle(), mainPostSaveDto.getContent(), user);
        log.info("저장된 글 번호 : {}", savenum);
        return "redirect:/mainpost/mainlist";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/put/{id}")
    public String mainpostput(@PathVariable Long id, Principal principal, MainPostSaveDto mainPostSaveDto){
        MainPostGetDto Dto = mainPostsService.getMainpost(id);
        if(Dto.getUser().getUsername().equals(principal.getName()) == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }
        mainPostSaveDto.setTitle(Dto.getTitle());
        mainPostSaveDto.setContent(Dto.getContent());
        return "MainPost_save";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/put/{id}")
    public String mainpostupdate(@PathVariable Long id, @Valid MainPostSaveDto mainPostSaveDto, BindingResult bindingResult, Principal principal) { //BindingResult 매개변수는 @Valid 애너테이션으로 인해 검증이 수행된 결과를 의미하는 객체이다.

        if (bindingResult.hasErrors()) {
            return "MainPost_save";
        }
        MainPostGetDto Dto = mainPostsService.getMainpost(id);
        if(Dto.getUser().getUsername().equals(principal.getName()) == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }
        long savenum = mainPostsService.modify(id, mainPostSaveDto.getTitle(), mainPostSaveDto.getContent());
        log.info("저장된 글 번호 : {}", savenum);
        return "redirect:/mainpost/comment/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String mainpoostdelete(@PathVariable Long id, Principal principal){
        MainPostGetDto Dto = mainPostsService.getMainpost(id);
        if(Dto.getUser().getUsername().equals(principal.getName()) == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }
        long deletenum = mainPostsService.delete(id);
        log.info("삭제된 글 번호 : {}", deletenum);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String mainpostlike(@PathVariable Long id, Principal principal){
        User user = this.userService.getbyUsername(principal.getName());
        this.mainPostsService.like(id, user);
        return "redirect:/mainpost/comment/" + id;
    }

}
