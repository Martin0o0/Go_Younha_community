package com.example.goyounhacom.web.Dto.UserDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.Users.Role;
import com.example.goyounhacom.domain.Users.User;
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


}
