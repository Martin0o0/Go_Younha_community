package com.example.goyounhacom.web.Dto.UserDto;

import com.example.goyounhacom.domain.Users.Role;
import com.example.goyounhacom.domain.Users.User;
import lombok.Getter;


@Getter
public class UserGetDto {

    private Long id;
    private String userid;
    private String password;
    private String email;
    private String nickname;
    private Boolean is_holics;
    private Long no_holics;
    private Role role;

    public UserGetDto(User entity){
        this.id = entity.getId();
        this.userid = entity.getUserid();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
        this.is_holics = entity.getIs_holics();
        this.no_holics = entity.getNo_holics();
        this.role = entity.getRole();
    }
}
