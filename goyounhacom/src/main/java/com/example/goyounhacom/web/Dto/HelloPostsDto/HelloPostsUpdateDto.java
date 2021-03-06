package com.example.goyounhacom.web.Dto.HelloPostsDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HelloPostsUpdateDto {


    private String content;

    private String username;

    @Builder
    public HelloPostsUpdateDto(Long id, String content, String username){
        this.content = content;
        this.username = username;
    }

}
