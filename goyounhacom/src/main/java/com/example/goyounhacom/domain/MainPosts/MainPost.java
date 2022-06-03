package com.example.goyounhacom.domain.MainPosts;

import com.example.goyounhacom.domain.Photo.Photo;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.baseTimeEntity.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@Getter
@Entity
public class MainPost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키, 자동증가
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = true)
    private Long fileId; //파일번호.


    @ManyToOne //여러개의 글이 한명의 작성자로 부터 생성 될 수 있기 때문에.
    @JoinColumn
    @JsonBackReference //순환참조 방지.
    private User user;

    private int viewcount; //조회수

    @OneToMany(mappedBy = "mainPost", cascade = CascadeType.REMOVE) //mappedby => 참조엔티티 속성명, cascade => 게시글 삭제하면 그 예하 묶인 놈들 싸그리 삭제함.
    private List<MainPostComment> mainPostComents; //1:N방식이니까. 부모엔티티가 자식엔티티를 여러개 가질 수 있어

    @OneToMany(mappedBy = "mainPost", cascade = CascadeType.REMOVE)
    private List<Recomment> mainPostRecomment;

    @ManyToMany //대등관계
    private Set<User> like;

    @Builder
    public MainPost(String title, String content, Long fileId,  User user){
        this.title = title;
        this.content = content;
        this.fileId = fileId;
        this.user = user;
    }

    public void update(String title, String content, Long fileId){
        this.title = title;
        this.content = content;
        this.fileId = fileId;
    }


}
