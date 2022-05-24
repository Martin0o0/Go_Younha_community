package com.example.goyounhacom.web.Controller.UserController;


import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.Service.ScrapService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserSaveDto;
import com.example.goyounhacom.web.Dto.UserDto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@Controller
public class UserPageController {

    private final UserService userService;

    private final ScrapService scrapService;

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
            bindingResult.rejectValue("username", "signupfailed", "이미 등록된 아이디 입니다.");
            return "sign-up-form";
        }
        else if(status == -2){
            bindingResult.rejectValue("nickname", "signupfailed", "이미 등록된 닉네임입니다.");
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update")
    public String userupdate(UserUpdateDto userUpdateDto, Principal principal){
        UserGetDto userGetDto = userService.findbyusername(principal.getName());
        userUpdateDto.setUsername(userGetDto.getUsername());
        userUpdateDto.setPassword(userGetDto.getPassword());
        userUpdateDto.setEmail(userGetDto.getEmail());
        userUpdateDto.setNickname(userGetDto.getNickname());
        return "User/user-update";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update")
    public String userupdate(@Valid UserUpdateDto userUpdateDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDatails principalDatails){

        if(bindingResult.hasErrors()){
            return "User/user-update";
        }

        long status = userService.updateUser(userUpdateDto, principalDatails);
        return "redirect:/";
    }





}
