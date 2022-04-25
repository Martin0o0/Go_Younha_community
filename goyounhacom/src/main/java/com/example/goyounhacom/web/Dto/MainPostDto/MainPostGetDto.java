package com.example.goyounhacom.web.Dto.MainPostDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MainPostGetDto {
    private Long id;

    private String title;

    private String content;

    private String username;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;


    public MainPostGetDto(MainPost entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.username = entity.getUsername();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
