package com.example.goyounhacom.web.Dto.HelloPostsDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.Users.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class HelloPostsGetDto {
    private Long id;

    private String title;

    private String content;

    private User user;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;


    public HelloPostsGetDto(HelloPost entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user = entity.getUser();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
