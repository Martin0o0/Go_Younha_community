package com.example.goyounhacom.web.Dto.HelloPostsDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class HelloPostsGetDto {
    private Long id;

    private String title;

    private String content;

    private String username;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;


    public HelloPostsGetDto(HelloPost entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.username = entity.getUsername();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
