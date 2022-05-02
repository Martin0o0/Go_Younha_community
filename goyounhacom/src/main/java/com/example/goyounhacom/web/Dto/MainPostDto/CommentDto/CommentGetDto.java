package com.example.goyounhacom.web.Dto.MainPostDto.CommentDto;

import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostComment;
import com.example.goyounhacom.domain.Users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class CommentGetDto {

    private String content;

    private User user;

    private MainPost mainPost;


    public CommentGetDto(MainPostComment entity) {
        this.content = entity.getContent();
        this.user = entity.getUser();
        this.mainPost = entity.getMainPost();
    }
}
