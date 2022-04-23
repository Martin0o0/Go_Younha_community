package com.example.goyounhacom.web.Controller.UserController;


import com.example.goyounhacom.Config.auth.PrincipalDetail;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.Users.UserRepository;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserSaveDto;
import com.example.goyounhacom.web.Dto.UserDto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/user")
public class UserApiController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public String signup(@RequestBody UserSaveDto userSaveDto) {
        log.info("유저 id : {}", userSaveDto.getUserid());
        log.info("유저 p/w : {}", userSaveDto.getPassword());
        log.info("유저 이메일: {}", userSaveDto.getEmail());
        log.info("유저 닉네임 : {}", userSaveDto.getNickname());
        log.info("홀릭스 여부  : {}", userSaveDto.getIs_holics());
        log.info("일반 사용자 여부 : {}", userSaveDto.getRole());
        return "등록된 회원 번호 : " + userService.save(userSaveDto);
    }

    @PutMapping("/update/{id}") //유저정보 업데이트
    public String updateuserinfo(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        log.info("수정된 패스워드 : {}",userUpdateDto.getPassword());
        log.info("수정된 이메일 : {}", userUpdateDto.getEmail());
        log.info("수정된 닉네임 : {}", userUpdateDto.getNickname());
        return "수정된 회원번호 : " + userService.update(id, userUpdateDto, principalDetail);


    }


    @GetMapping("/get")
    public List<UserGetDto> findbyall() {
        return userService.findByAll();
    }

    @GetMapping("/get/{id}")
    public UserGetDto findbyid(@PathVariable Long id) {
        return userService.findbyid(id);
    }

    @GetMapping("/get/{userid}")
    public UserGetDto findbyuserid(@PathVariable String userid) {
        return userService.findbyuserid(userid);
    }

    @DeleteMapping("/delete/{id}")
    public String deletebyid(@PathVariable Long id) {
        return userService.deletebyid(id);
    }


}
