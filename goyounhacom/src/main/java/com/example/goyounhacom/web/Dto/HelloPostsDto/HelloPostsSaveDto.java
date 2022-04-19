package com.example.goyounhacom.web.Dto.HelloPostsDto;


import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class HelloPostsSaveDto {

    private String title;

    private String content;

    private String userId;

    @Builder
    public HelloPostsSaveDto(String title, String content, String userId){
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public HelloPost toEntity(){
        return HelloPost.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .build();
    }
}
