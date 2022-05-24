package com.example.goyounhacom.web.Dto.MainPostDto.CommentDto;

import com.example.goyounhacom.domain.MainPosts.MainPostComment;
import com.example.goyounhacom.domain.MainPosts.Recomment;
import com.example.goyounhacom.domain.Users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecommentGetDto {
    private String content;

    private User user;

    private MainPostComment mainPostComment;

    public RecommentGetDto(Recomment recomment){
        this.content = recomment.getContent();
        this.user = recomment.getUser();
        this.mainPostComment = recomment.getMainPostComment();
    }
}
