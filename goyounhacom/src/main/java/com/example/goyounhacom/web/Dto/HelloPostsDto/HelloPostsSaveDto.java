package com.example.goyounhacom.web.Dto.HelloPostsDto;


import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.Users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class HelloPostsSaveDto {


    @NotEmpty(message = "제목은 필수입니다.")
    @Size(max=100, message = "100자 이하로 해주세요.")
    private String title;

    @NotEmpty(message = "내용을 적어주세요.")
    @Size(max=400, message = "400자 이내로 적어주세요.")
    private String content;

    private User user;

    @Builder
    public HelloPostsSaveDto(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public HelloPost toEntity(){
        return HelloPost.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
