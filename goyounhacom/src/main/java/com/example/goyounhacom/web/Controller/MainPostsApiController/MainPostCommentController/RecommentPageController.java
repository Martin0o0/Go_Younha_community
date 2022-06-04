package com.example.goyounhacom.web.Controller.MainPostsApiController.MainPostCommentController;


import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.Service.CommentService;
import com.example.goyounhacom.Service.MainPostsService;
import com.example.goyounhacom.Service.RecommentService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.MainPosts.Recomment;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentGetDto;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.RecommentGetDto;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.RecommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mainpost")
public class RecommentPageController {
    private final UserService userService;
    private final CommentService commentService;
    private final RecommentService recommentService;
    private final MainPostsService mainPostsService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/recomment/post/{commentid}")
    public String createRecomment(@PathVariable Long commentid, Model model, @ModelAttribute @Valid RecommentSaveDto recommentSaveDto, BindingResult bindingResult, CommentSaveDto commentSaveDto, Principal principal, RedirectAttributes abbtribute) {
        User user = userService.getbyUsername(principal.getName());
        CommentGetDto commentGetDto = commentService.getComment(commentid);
        if (bindingResult.hasErrors()) {
            log.info("에러발생시점");
            MainPostGetDto mainPostGetDto = mainPostsService.getMainpost(commentGetDto.getMainPost().getId());
            model.addAttribute("main_post", mainPostGetDto);
            model.addAttribute("userinfo", user);
            if (recommentService.existbymainpostid(commentGetDto.getMainPost().getId())) {
                List<Recomment> list = recommentService.findallbymainpostid(commentGetDto.getMainPost().getId());
                model.addAttribute("recomment", list);
            }
            abbtribute.addFlashAttribute("errorpoint", commentid);
            abbtribute.addFlashAttribute("iserror", "true");
            return "redirect:/mainpost/comment/" + commentGetDto.getMainPost().getId() + "#comment_" + commentid; //앵커
        }

        log.info("content : {}", recommentSaveDto.getContent());
        Long postid = recommentService.saveRecomment(commentid, recommentSaveDto.getContent(), user);
        return "redirect:/mainpost/comment/" + commentGetDto.getMainPost().getId() + "#comment_" + commentid; //앵커
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recomment/put/{id}")
    public String recommentmodify(RecommentSaveDto recommentSaveDto, @PathVariable Long id, Principal principal, @AuthenticationPrincipal PrincipalDatails principalDatails, Model model) {
        RecommentGetDto recommentGetDto = recommentService.getRecomment(id);
        if (recommentGetDto.getUser().getUsername().equals(principal.getName()) == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }
        recommentSaveDto.setContent(recommentGetDto.getContent());
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }
        return "recomment";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/recomment/put/{id}")
    public String recommentUpdate(@Valid RecommentSaveDto recommentSaveDto, BindingResult bindingResult, @PathVariable Long id, Principal principal, @AuthenticationPrincipal PrincipalDatails principalDatails, Model model) {
        if (bindingResult.hasErrors()) {
            if (principalDatails != null) {
                User user = userService.getbyUsername(principalDatails.getUsername());
                model.addAttribute("userinfo", user);
            }
            return "recomment";
        }
        RecommentGetDto recommentGetDto = recommentService.getRecomment(id);
        CommentGetDto commentGetDto = commentService.getComment(recommentGetDto.getMainPostComment().getId());
        if (recommentGetDto.getUser().getUsername().equals(principal.getName()) == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }
        long savenum = recommentService.modify(id, recommentSaveDto.getContent());
        log.info("저장된 글 번호 : {}", savenum);
        return "redirect:/mainpost/comment/" + commentGetDto.getMainPost().getId() + "#comment_" + recommentGetDto.getMainPostComment().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recomment/delete/{id}")
    public String commentdelete(@PathVariable Long id, Principal principal) {
        RecommentGetDto recommentGetDto = recommentService.getRecomment(id);
        CommentGetDto commentGetDto = commentService.getComment(recommentGetDto.getMainPostComment().getId());
        User user = userService.getbyUsername(principal.getName());
        if (user.getRoleKey().contentEquals("ROLE_ADMIN")) {
            long deletenum = recommentService.delete(id);
            return "redirect:/mainpost/comment/" + commentGetDto.getMainPost().getId() + "#comment " + recommentGetDto.getMainPostComment().getId();
        } else if (commentGetDto.getUser().getUsername().equals(principal.getName()) == true) {
            long deletenum = recommentService.delete(id);
            return "redirect:/mainpost/comment/" + commentGetDto.getMainPost().getId() + "#comment " + recommentGetDto.getMainPostComment().getId();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없음");
        }
    }
}
