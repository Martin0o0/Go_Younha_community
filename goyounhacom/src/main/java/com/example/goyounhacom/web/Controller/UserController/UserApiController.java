package com.example.goyounhacom.web.Controller.UserController;


import com.example.goyounhacom.Service.ScrapService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.Users.UserRepository;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserLoginDto;
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

    private final ScrapService scrapService;

    @PostMapping("/sign-up")
    public String signup(@RequestBody UserSaveDto userSaveDto) {
        long status = userService.save(userSaveDto);
        if(status == -1){
            return "이미 등록된 아이디가 있습니다.";
        }
        else {
            log.info("유저 id : {}", userSaveDto.getUsername());
            log.info("유저 p/w : {}", userSaveDto.getPassword());
            log.info("유저 이메일: {}", userSaveDto.getEmail());
            log.info("유저 닉네임 : {}", userSaveDto.getNickname());
            log.info("홀릭스 여부  : {}", userSaveDto.getIs_holics());
            log.info("일반 사용자 여부 : {}", userSaveDto.getRole());

            return "등록된 번호 : " + status;
        }
    }

    @PutMapping("/update/{id}") //유저정보 업데이트
    public String updateuserinfo(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        log.info("수정된 패스워드 : {}",userUpdateDto.getPassword());
        log.info("수정된 이메일 : {}", userUpdateDto.getEmail());
        log.info("수정된 닉네임 : {}", userUpdateDto.getNickname());
        return "수정된 회원번호 : " + userService.update(id, userUpdateDto);
    }


    @GetMapping("/updateholics/{id}")
    public String updateholics(@PathVariable Long id){
        return "홀릭스 등업 회원 번호 : " + userService.updateholics(id);
    }

    @GetMapping("/updateAdmin/{username}")
    public void updateAdmin(@PathVariable String username){
        userService.updateAdmin(username);
    }


    @PostMapping("/scrap/{username}")
    public void savepost(@PathVariable String username, @RequestParam Long postid) {
        scrapService.save(username, postid);
        log.info("{}의 스크랩에 등록된  게시글 번호 : {}", username, postid);
    }

    @DeleteMapping("/scrap/{username}")
    public void deletepost(@PathVariable String username, @RequestParam Long postid){
        scrapService.delete(username, postid);
        log.info("{}의 스크랩에 제거된 게시글 번호 : {} ", username, postid);
    }



    @GetMapping("/get")
    public List<UserGetDto> findbyall() {
        return userService.findByAll();
    }

    @GetMapping("/get/id/{id}")
    public UserGetDto findbyid(@PathVariable Long id) {
        return userService.findbyid(id);
    }

    @GetMapping("/get/userid/{userid}")
    public UserGetDto findbyusername(@PathVariable String userid) {
        return userService.findbyusername(userid);
    }




}
