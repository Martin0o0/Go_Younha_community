package com.example.goyounhacom.web.Dto.UserDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.Users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class UserUpdateDto {

    private String username;

    @NotEmpty(message = "비밀번호 항목은 필수입니다.")
    private String password;

    @NotEmpty(message = "이메일 항목은 필수입니다.")
    @Email
    private String email;

    @NotEmpty(message = "닉네임 항목은 필수입니다.")
    private String nickname;

    @Builder
    public UserUpdateDto(String password, String email, String nickname) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){ //암호화 한 후 다시 세터로 설정.
        this.password = password;
    }

}