package com.example.goyounhacom.web.Controller.UserController;


import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.UserDto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public String signup(@RequestBody UserSaveDto userSaveDto){
        log.info("유저 id : {}", userSaveDto.getUserid());
        log.info("유저 p/w : {}", userSaveDto.getPassword());
        log.info("유저 이메일: {}", userSaveDto.getEmail());
        log.info("유저 닉네임 : {}", userSaveDto.getNickname());
        log.info("유저  : {}", userSaveDto.getIs_holics());
        log.info("일반 사용자 여부 : {}" , userSaveDto.getRole());
        return "등록된 글 번호 : " + userService.save(userSaveDto);
    }
}
