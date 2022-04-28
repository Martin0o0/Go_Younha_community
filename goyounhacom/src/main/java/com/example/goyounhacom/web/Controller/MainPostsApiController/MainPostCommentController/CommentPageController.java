package com.example.goyounhacom.web.Controller.MainPostsApiController.MainPostCommentController;

import com.example.goyounhacom.Service.CommentService;
import com.example.goyounhacom.Service.MainPostsService;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import groovy.beans.Bindable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mainpost")
public class CommentPageController {
    private final CommentService commentService;
    private final MainPostsService mainPostsService;

    @PostMapping("/comment/post/{id}")
    public String createComment(@PathVariable long id, Model model, @Valid CommentSaveDto commentSaveDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            MainPostGetDto mainPostGetDto = mainPostsService.getMainpost(id);
            model.addAttribute("main_post", mainPostGetDto);
            return "MainPost_comment";
        }
        Long postid = commentService.saveComment(id, commentSaveDto.getUsername(), commentSaveDto.getContent());
        return "redirect:/mainpost/comment/"+ id;
    }


}
