package com.example.goyounhacom.web.Controller.MainPostsApiController.MainPostCommentController;

import com.example.goyounhacom.Service.CommentService;
import com.example.goyounhacom.Service.MainPostsService;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mainpost")
public class CommentPageController {
    private final CommentService commentService;

    @PostMapping("/comment/post/{id}")
    public String createComment(@PathVariable long id, Model model,@RequestParam String username, @RequestParam String content){
        Long postid = commentService.saveComment(id, username, content);
        return "redirect:/mainpost/comment/"+ id;
    }


}
