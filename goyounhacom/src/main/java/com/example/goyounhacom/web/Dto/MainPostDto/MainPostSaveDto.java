package com.example.goyounhacom.web.Dto.MainPostDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MainPostSaveDto {

    private String title;

    private String content;

    private String username;

    @Builder
    public MainPostSaveDto(String title, String content, String userId){
        this.title = title;
        this.content = content;
        this.username = userId;
    }

    public MainPost toEntity(){
        return MainPost.builder()
                .title(title)
                .content(content)
                .username(username)
                .build();
    }
}