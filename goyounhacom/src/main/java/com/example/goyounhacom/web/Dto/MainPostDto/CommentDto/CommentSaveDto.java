package com.example.goyounhacom.web.Dto.MainPostDto.CommentDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostComment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class CommentSaveDto {

    private String content;
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