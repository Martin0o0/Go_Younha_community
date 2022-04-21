package com.example.goyounhacom.web.Dto.UserDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.Users.Role;
import com.example.goyounhacom.domain.Users.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSaveDto {

        private String userid;
        private String password;
        private String email;
        private String nickname;
        private Boolean is_holics;
        private Long no_holics;
        private Role role;

        @Builder
        public UserSaveDto(String userid, String password, String email, String nickname){
                this.userid = userid;
                this.password = password;
                this.email = email;
                this.nickname = nickname;
                this.is_holics = false;
                this.no_holics = null;
                this.role = Role.USER;
        }


        public User toEntity(){
                return User.builder()
                        .userid(userid)
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
