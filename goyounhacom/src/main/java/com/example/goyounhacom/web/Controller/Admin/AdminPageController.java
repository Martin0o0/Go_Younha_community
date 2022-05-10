package com.example.goyounhacom.web.Controller.Admin;


import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminPageController {
    private final UserService userService;


    @GetMapping()
    public String viewAdmin(Model model){
        return "admin/Admin";
    }

    @GetMapping("/postlist")
    public String viewpostlist(Model model){
        model.addAttribute("postlsit", "admin/postlist");
        return "admin/mainpostindex";
    }

    @GetMapping("/users")
    public String viewaccount(Model model){
        List<UserGetDto> list = userService.findByAll();
        model.addAttribute("userGetDto", list);
        return "admin/adminusersindex";
    }

    @GetMapping("/userinfo/{id}")
    public String moreinfouser(@PathVariable Long id, Model model){
        UserGetDto userGetDto = userService.findbyid(id);
        model.addAttribute("user", userGetDto);
        return "admin/User-info";
    }

    @DeleteMapping("/delete/user/{id}")
    public String deletebyid(@PathVariable Long id) {
        log.info("{}", userService.deletebyid(id));
        return "rediect:/admin/users";
    }

    @GetMapping("/updateholics/{id}")
    public String updateholics(@PathVariable Long id){
        log.info("등업된 회원번호 : {} ", userService.updateholics(id));
        return "redirect:/admin/userinfo/" + id;
    }

    @GetMapping("/updateAdmin/{username}")
    public String updateAdmin(@PathVariable String username){
        long id = userService.updateAdmin(username);
        log.info("등업된 회원번호 : {} ", id);
        return "redirect:/admin/userinfo/" + id;
    }

}
