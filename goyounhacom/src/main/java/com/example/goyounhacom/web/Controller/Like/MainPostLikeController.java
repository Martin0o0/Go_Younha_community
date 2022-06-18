package com.example.goyounhacom.web.Controller.Like;


import com.example.goyounhacom.Service.MainPostLikeService;
import com.example.goyounhacom.Service.MainPostsService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.MainPostLike.MainPostLikeRepository;
import com.example.goyounhacom.domain.Users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@RequestMapping("/mainpost")
@RequiredArgsConstructor
@Controller
public class MainPostLikeController {
    private final UserService userService;

    private final MainPostLikeService mainPostLikeService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String mainpostlike(@PathVariable Long id, Principal principal) {
        User user = this.userService.getbyUsername(principal.getName());
        Boolean haslike = mainPostLikeService.addordeleteLike(user,id); //true : 저장됨-> 존재 <-> false : 삭제됨 -> 없음
        return "redirect:/mainpost/comment/" + id;
    }

}
