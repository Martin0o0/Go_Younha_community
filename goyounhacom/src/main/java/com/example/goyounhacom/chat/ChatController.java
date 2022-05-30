package com.example.goyounhacom.chat;


import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.Users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/chat")
    public String chatGET(@AuthenticationPrincipal PrincipalDatails principalDatails, Model model){
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("user_info", user);
        }
        log.info("@ChatController, chat GET()");

        return "chat";
    }
}
