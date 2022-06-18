package com.example.goyounhacom.web.Controller;


import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.Service.*;
import com.example.goyounhacom.domain.Infomation.Notice;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.chat.RoomId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.model.IModel;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainPageController {
    private final UserService userService;
    private final ChatService chatService;
    private final NoticeService noticeService;
    private final MainPostsService mainPostsService;

    private final MainPostLikeService mainPostLikeService;

    @GetMapping("/") //루트 다이렉트
    public String root(@AuthenticationPrincipal PrincipalDatails principalDatails, Model model){
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("user_info", user);
            List<RoomId> list = chatService.findListuserid(user.getUsername());
            model.addAttribute("usernamelist", list);
        }
        List<Notice> noticelist = noticeService.findByTop3List();
        model.addAttribute("noticelist", noticelist);
        List<MainPost> mainpostlist = mainPostsService.findByAll();
        List<MainPost> top3list = new ArrayList<>();
        int cnt = 0;
        for(int i = mainpostlist.size()-1; i >=0; i--){
            if(cnt == 3){
                break;
            }
            if(mainPostLikeService.getCountLike(mainpostlist.get(i).getId()) >= 5){
                top3list.add(mainpostlist.get(i));
                cnt++;
            }
        }
        model.addAttribute("mainpostlist", top3list);
        return "Main";
    }

}
