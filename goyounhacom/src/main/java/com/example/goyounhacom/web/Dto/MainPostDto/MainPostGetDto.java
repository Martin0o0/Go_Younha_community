package com.example.goyounhacom.web.Dto.MainPostDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostComment;
import com.example.goyounhacom.domain.MainPosts.Recomment;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.MainPostDto.FileDto.MainPostFileDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
public class MainPostGetDto {
    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Long fileId;

    private User user;

    private int viewcount;

    private List<MainPostComment> list;

    private List<Recomment> recommentList;



    public MainPostGetDto(MainPost entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.fileId = entity.getFileId();
        this.user = entity.getUser();
        this.viewcount = entity.getViewcount();
        this.list = entity.getMainPostComents();
        this.recommentList = entity.getMainPostRecomment();

    }
}
