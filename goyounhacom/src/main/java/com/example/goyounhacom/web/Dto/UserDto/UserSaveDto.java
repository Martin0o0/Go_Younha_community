package com.example.goyounhacom.web.Dto.UserDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.Users.Role;
import com.example.goyounhacom.domain.Users.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class UserSaveDto {
        @Size(min = 3, max = 20, message = "3~20 자리 이내")
        @NotEmpty(message ="아이디 항목은 필수입니다.")
        private String username;

        @NotEmpty(message = "비밀번호 항목은 필수입니다.")
        private String password;

        @NotEmpty(message = "이메일 항목은 필수입니다.")
        @Email
        private String email;

        @NotEmpty(message = "닉네임 항목은 필수입니다.")
        private String nickname;

        private Boolean is_holics;
        private Long no_holics;
        private Role role;

        @Builder
        public UserSaveDto(String username, String password, String email, String nickname){
                this.username = username;
                this.password = password;
                this.email = email;
                this.nickname = nickname;
                this.is_holics = false;
                this.no_holics = null;
                this.role = Role.USER;
        }


        public User toEntity(){
                return User.builder()
                        .username(username)
                        .password(password)
                        .email(email)
                        .nickname(nickname)
                        .isholics(false)
                        .noholics(null)
                        .role(Role.USER)
                        .build();
        }


        public void setPassword(String password){ //암호화 한 후 다시 세터로 설정.
                this.password = password;
        }




}
