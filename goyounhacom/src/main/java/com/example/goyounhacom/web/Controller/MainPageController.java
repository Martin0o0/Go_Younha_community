package com.example.goyounhacom.web.Controller;


import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.Service.ChatService;
import com.example.goyounhacom.Service.UserService;
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

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainPageController {
    private final UserService userService;
    private final ChatService chatService;

    @GetMapping("/") //루트 다이렉트
    public String root(@AuthenticationPrincipal PrincipalDatails principalDatails, Model model){
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("user_info", user);
            List<RoomId> list = chatService.findListuserid(user.getUsername());
            model.addAttribute("usernamelist", list);
        }



        return "Main";
    }

}
