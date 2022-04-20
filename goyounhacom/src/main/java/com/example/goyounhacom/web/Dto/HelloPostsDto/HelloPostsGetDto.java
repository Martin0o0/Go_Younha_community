package com.example.goyounhacom.web.Dto.HelloPostsDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HelloPostsGetDto {
    private Long id;

    private String title;

    private String content;

    private String userId;

    public HelloPostsGetDto(HelloPost entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.userId = entity.getUserId();
    }


}
