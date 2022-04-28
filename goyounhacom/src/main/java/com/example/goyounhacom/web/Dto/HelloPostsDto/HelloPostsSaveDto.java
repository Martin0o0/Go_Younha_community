package com.example.goyounhacom.web.Dto.HelloPostsDto;


import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class HelloPostsSaveDto {


    private String title;


    private String content;


    private String username;

    @Builder
    public HelloPostsSaveDto(String title, String content, String username){
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public HelloPost toEntity(){
        return HelloPost.builder()
                .title(title)
                .content(content)
                .username(username)
                .build();
    }
}
