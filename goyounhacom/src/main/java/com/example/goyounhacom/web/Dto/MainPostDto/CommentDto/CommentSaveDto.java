package com.example.goyounhacom.web.Dto.MainPostDto.CommentDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostComment;
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

    @NotEmpty(message = "이름 필수입니다.")
    private String username;

    private MainPost mainPost;

    @Builder
    public CommentSaveDto(String content, String username, MainPost mainpost){
        this.content = content;
        this.username = username;
        this.mainPost = mainpost;
    }

    public MainPostComment toEntity(){
        return MainPostComment.builder()
                .content(content)
                .username(username)
                .mainPost(mainPost)
                .build();
    }


}