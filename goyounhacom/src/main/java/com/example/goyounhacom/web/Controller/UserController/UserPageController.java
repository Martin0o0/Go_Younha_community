package com.example.goyounhacom.web.Controller.UserController;


import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.web.Dto.UserDto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@Controller
public class UserPageController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "log-in-form";
    }



    @GetMapping("/sign-up")
    public String signup(UserSaveDto userSaveDto){
        return "sign-up-form";
    }


    @PostMapping("/sign-up")
    public String signup(@Valid UserSaveDto userSaveDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "sign-up-form";
        }

        long status = userService.save(userSaveDto);
        log.info("회원번호 : {}", status);

        if(status == -1){
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "sign-up-form";
        }
        else {
            log.info("");
            log.info("유저 id : {}", userSaveDto.getUsername());
            log.info("유저 p/w : {}", userSaveDto.getPassword());
            log.info("유저 이메일: {}", userSaveDto.getEmail());
            log.info("유저 닉네임 : {}", userSaveDto.getNickname());
            log.info("홀릭스 여부  : {}", userSaveDto.getIs_holics());
            log.info("일반 사용자 여부 : {}", userSaveDto.getRole());

            return "redirect:/";
        }
    }
}
