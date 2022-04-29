package com.example.goyounhacom.web.Dto.MainPostDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostComment;
import com.example.goyounhacom.domain.Users.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MainPostGetDto {
    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private User user;

    private List<MainPostComment> list;

    public MainPostGetDto(MainPost entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.user = entity.getUser();
        this.list = entity.getMainPostComents();
    }
}
