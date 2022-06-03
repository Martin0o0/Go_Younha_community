package com.example.goyounhacom.domain.MainPosts;

import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.baseTimeEntity.BaseTimeEntity;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
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
    @JoinColumn
    @JsonBackReference //순환참조 방지.
    private User user;

    @ManyToOne
    //부모 객체의 식별자에 해당하는 참조키로 설정하는 어노테이션
    @JoinColumn
    @JsonBackReference //순환참조 방지.
    private MainPost mainPost; //자식이니까 N:1방식이다.


    @OneToMany(mappedBy = "mainPostComment", cascade = CascadeType.REMOVE, orphanRemoval = true) //mappedby => 참조엔티티 속성명, cascade => 게시글 삭제하면 그 예하 묶인 놈들 싸그리 삭제함.
    private List<Recomment> mainPostComents; //1:N방식이니까. 부모엔티티가 자식엔티티를 여러개 가질 수 있어

    @ManyToMany //마찬가지로 대등관계이므로
    private Set<User> like;


    @Builder
    public MainPostComment(String content, User user, MainPost mainPost){
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
