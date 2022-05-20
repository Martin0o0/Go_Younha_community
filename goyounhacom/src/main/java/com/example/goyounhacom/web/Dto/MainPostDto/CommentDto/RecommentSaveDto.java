package com.example.goyounhacom.web.Dto.MainPostDto.CommentDto;


import com.example.goyounhacom.Service.CommentService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostComment;
import com.example.goyounhacom.domain.MainPosts.MainPostCommentRepository;
import com.example.goyounhacom.domain.MainPosts.Recomment;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.Users.UserRepository;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserSaveDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotEmpty;


@Slf4j
@Getter
@NoArgsConstructor
@Setter
public class RecommentSaveDto {
    @NotEmpty(message = "내용을 채워주세요.")
    private String content;

    private User user;

    private MainPostComment mainPostComment;


    @Builder
    public RecommentSaveDto(String content,User user , MainPostComment mainPostComment){
        this.content = content;
        this.user = user;
        this.mainPostComment = mainPostComment;
    }

    public Recomment toEntity(){
        return Recomment.builder()
                .content(content)
                .user(user)
                .mainPostComment(mainPostComment)
                .build();
    }


}
