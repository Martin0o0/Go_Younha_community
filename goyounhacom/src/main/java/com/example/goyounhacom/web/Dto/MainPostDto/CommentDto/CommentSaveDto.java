package com.example.goyounhacom.web.Dto.MainPostDto.CommentDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostComment;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.bridge.Message;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class CommentSaveDto {

    @NotEmpty(message="내용필수입니다.")
    private String content;

    private User user;

    private MainPost mainPost;

    @Builder
    public CommentSaveDto(String content, MainPost mainpost, User user){
        this.content = content;
        this.mainPost = mainpost;
        this.user = user;
    }

    public MainPostComment toEntity(){
        return MainPostComment.builder()
                .content(content)
                .mainPost(mainPost)
                .user(user)
                .build();
    }


}