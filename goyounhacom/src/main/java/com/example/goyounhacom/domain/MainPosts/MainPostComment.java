package com.example.goyounhacom.domain.MainPosts;

import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.baseTimeEntity.BaseTimeEntity;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

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
    //부모 객체의 식별자에 해당하는 참조키로 설정하는 어노테이션
    @JoinColumn
    @JsonBackReference //순환참조 방지.
    private MainPost mainPost; //자식이니까 N:1방식이다.

    @ManyToMany //마찬가지로 대등관계이므로
    private Set<User> like;


    @Builder
    public MainPostComment(String title, String content, User user, MainPost mainPost){
        this.content = content;
        this.user = user;
        this.mainPost = mainPost;
    }

    @Override
    public String toString() {
        return "MainPostComment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", mainPost=" + mainPost +
                ", like=" + like +
                '}';
    }

    public void update(String content){
        this.content = content;
    }
}
