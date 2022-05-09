package com.example.goyounhacom.web.Controller.Admin;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @GetMapping("/")
    public RedirectView viewAdmin(Model model){
        return new RedirectView("admin/admin");
    }

    @GetMapping("/postlist")
    public String viewpostlist(Model model){
        model.addAttribute("postlsit", "admin/postlist");
        return "admin/mainpostindex";
    }

    @GetMapping("/users")
    public String viewaccount(Model model){
        model.addAttribute("users", "admin/users");
        return "admin/usersindex";
    }
}
