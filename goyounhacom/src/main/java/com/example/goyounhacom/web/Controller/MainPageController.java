package com.example.goyounhacom.web.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {

    @GetMapping("/") //루트 다이렉트
    public String root() {
        return "Main";
    }

}
