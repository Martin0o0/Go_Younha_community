package com.example.goyounhacom.web.Dto.Imformation;


import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.Infomation.Notice;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class NoticeSaveDto {

    @NotEmpty(message = "내용을 적어주세요")
    @Size(max=100, message = "100자 이내로 적어주세요")
    private String title;


    @NotEmpty(message = "내용을 적어주세요")
    @Size(max=400, message = "400자 이내로 적어주세요")
    private String content;

    private User user;

    @Builder
    public NoticeSaveDto(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Notice toEntity(){
        return Notice.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }

}
