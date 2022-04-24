package com.example.goyounhacom.web.Dto.UserDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginDto {
    String username;
    String password;

    @Builder
    public UserLoginDto(String username, String password){
        this.username = username;
        this.password = password;
    }
}
