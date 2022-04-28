package com.example.goyounhacom.web.Dto.MainPostDto;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class MainPostSaveDto {
    @NotEmpty(message = "제목은 필수입니다.")
    @Size(max=100, message = "100자 이하로 해주세요.")
    private String title;
    @NotEmpty(message = "내용을 적어주세요.")
    @Size(max=400, message = "400자 이내로 적어주세요.")
    private String content;

    @NotEmpty(message = "이름 필수입니다.")
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