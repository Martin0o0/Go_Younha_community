package com.example.goyounhacom.web.Dto.UserDto;

import com.example.goyounhacom.domain.Users.Role;
import com.example.goyounhacom.domain.Users.User;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class UserGetDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private Boolean is_holics;
    private Long no_holics;
    private Role role;
    private LocalDateTime createdDate;

    public UserGetDto(User entity){
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
        this.is_holics = entity.getIs_holics();
        this.no_holics = entity.getNo_holics();
        this.role = entity.getRole();
        this.createdDate = entity.getCreatedDate();
    }
}
