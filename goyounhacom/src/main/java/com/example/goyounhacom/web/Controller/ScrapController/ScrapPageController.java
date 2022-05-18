package com.example.goyounhacom.web.Controller.ScrapController;


import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.Service.ScrapService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.Scrap.Scrap;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/auth/user")
public class ScrapPageController {
    private final UserService userService;
    private final ScrapService scrapService;


    @GetMapping("/scrap/{userid}")
    public String scrapList( @AuthenticationPrincipal PrincipalDatails principalDatails , Model model, @PathVariable Long userid, @PageableDefault(size = 5)Pageable pageable){
        log.info("회원번호 : {}", userid);
        Page<Scrap> list = scrapService.getList(userid, pageable);
        Page<MainPost> postlist = list.map(dto -> dto.getMainPost());
        model.addAttribute("main_post_scrap", postlist);
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }

        //Pagealbe로 변환.
        return "User/UserScrap";
    }
}
