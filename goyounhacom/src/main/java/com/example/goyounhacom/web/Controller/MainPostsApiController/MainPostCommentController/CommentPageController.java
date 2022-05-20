package com.example.goyounhacom.web.Controller.MainPostsApiController.MainPostCommentController;

import com.example.goyounhacom.Service.CommentService;
import com.example.goyounhacom.Service.MainPostsService;
import com.example.goyounhacom.Service.RecommentService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.MainPosts.Recomment;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentGetDto;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.RecommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserSaveDto;
import groovy.beans.Bindable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.xml.stream.events.Comment;
import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mainpost")
public class CommentPageController {
    private final CommentService commentService;
    private final MainPostsService mainPostsService;
    private final UserService userService;
    private final RecommentService recommentService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/post/{id}")
    public String createComment(@PathVariable long id, Model model,@ModelAttribute @Valid CommentSaveDto commentSaveDto ,BindingResult bindingResult, RecommentSaveDto recommentSaveDto ,Principal principal){
        //현재 로그인한 사용자에 대한 정보를 알기 위해서는 스프링 시큐리티가 제공하는 Principal 객체를 사용해야 한다.
        User user = userService.getbyUsername(principal.getName());
        if(bindingResult.hasErrors()){
            MainPostGetDto mainPostGetDto = mainPostsService.getMainpost(id);
            model.addAttribute("main_post", mainPostGetDto);
            model.addAttribute("userinfo", user);
            if(recommentService.existbymainpostid(id)){
                List<Recomment> list = recommentService.findallbymainpostid(id);
                model.addAttribute("recomment", list);
            }
            model.addAttribute("iserror", null);
            model.addAttribute("errorpoint", null);
            return "MainPost_comment";
        }
        Long postid = commentService.saveComment(id, commentSaveDto.getContent(), user);
        return "redirect:/mainpost/comment/"+ id + "#comment_" + postid; //앵커
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/comment/put/{id}")
    public String commentmodify(CommentSaveDto commentSaveDto,  @PathVariable Long id, Principal principal){
        CommentGetDto commentGetDto = commentService.getComment(id);
        if(commentGetDto.getUser().getUsername().equals(principal.getName()) == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }
        commentSaveDto.setContent(commentGetDto.getContent());
        return "comment";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/put/{id}")
    public String CommentUpdate(@Valid CommentSaveDto commentSaveDto, BindingResult bindingResult, @PathVariable Long id, Principal principal){
        if (bindingResult.hasErrors()) {
            return "comment";
        }
        CommentGetDto Dto = commentService.getComment(id);
        if(Dto.getUser().getUsername().equals(principal.getName()) == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }
        long savenum = commentService.modify(id, commentSaveDto.getContent());
        log.info("저장된 글 번호 : {}", savenum);
        return "redirect:/mainpost/comment/" + Dto.getMainPost().getId() + "#comment_" + savenum;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/comment/delete/{id}")
    public String commentdelete(@PathVariable Long id, Principal principal){
        CommentGetDto commentGetDto = commentService.getComment(id);
        User user = userService.getbyUsername(principal.getName());
        if(user.getRoleKey().contentEquals("ROLE_ADMIN")){
            long deletenum = commentService.delete(id);
            return "redirect:/mainpost/comment/" + commentGetDto.getMainPost().getId();
        }
        else if(commentGetDto.getUser().getUsername().equals(principal.getName()) == true){
            long deletenum = commentService.delete(id);
            return "redirect:/mainpost/comment/" + commentGetDto.getMainPost().getId();
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없음");
        }

    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/comment/like/{id}")
    public String commentlike(@PathVariable Long id, Principal principal){
        User user = this.userService.getbyUsername(principal.getName());
        CommentGetDto commentGetDto = commentService.getComment(id);
        this.commentService.like(id, user);
        return "redirect:/mainpost/comment/" + commentGetDto.getMainPost().getId() + "#comment_" + id;
    }





}
