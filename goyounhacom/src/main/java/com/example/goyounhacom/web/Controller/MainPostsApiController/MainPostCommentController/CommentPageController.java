package com.example.goyounhacom.web.Controller.MainPostsApiController.MainPostCommentController;

import com.example.goyounhacom.Service.CommentService;
import com.example.goyounhacom.Service.MainPostsService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserSaveDto;
import groovy.beans.Bindable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mainpost")
public class CommentPageController {
    private final CommentService commentService;
    private final MainPostsService mainPostsService;
    private final UserService userService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/post/{id}")
    public String createComment(@PathVariable long id, Model model, @Valid CommentSaveDto commentSaveDto, BindingResult bindingResult, Principal principal){
        //현재 로그인한 사용자에 대한 정보를 알기 위해서는 스프링 시큐리티가 제공하는 Principal 객체를 사용해야 한다.
        User user = userService.getbyUsername(principal.getName());
        if(bindingResult.hasErrors()){
            MainPostGetDto mainPostGetDto = mainPostsService.getMainpost(id);
            model.addAttribute("main_post", mainPostGetDto);
            return "MainPost_comment";
        }
        Long postid = commentService.saveComment(id, commentSaveDto.getContent(), user);
        return "redirect:/mainpost/comment/"+ id;
    }


}
