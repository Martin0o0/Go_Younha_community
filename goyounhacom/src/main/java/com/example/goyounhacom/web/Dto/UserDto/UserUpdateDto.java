package com.example.goyounhacom.web.Dto.UserDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.Users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateDto {
    private String password;
    private String email;
    private String nickname;

    @Builder
    public UserUpdateDto(String password, String email, String nickname) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public void setPassword(String password){ //암호화 한 후 다시 세터로 설정.
        this.password = password;
    }

}