package com.example.goyounhacom.domain.MainPosts;

import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.baseTimeEntity.BaseTimeEntity;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MainPostComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키, 자동증가
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private MainPost mainPost; //자식이니까 N:1방식이다.

    @Builder
    public MainPostComment(String title, String content, User user, MainPost mainPost){
        this.content = content;
        this.user = user;
        this.mainPost = mainPost;
    }

    public void update(String content){
        this.content = content;
    }
}
