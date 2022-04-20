package com.example.goyounhacom.web.Dto.HelloPostsDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HelloPostsUpdateDto {

    private String title;

    private String content;

    private String userId;

    @Builder
    public HelloPostsUpdateDto(Long id, String title, String content, String userId){
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

}
