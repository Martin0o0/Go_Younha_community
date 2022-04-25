package com.example.goyounhacom.domain.MainPosts;

import com.example.goyounhacom.domain.baseTimeEntity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.List;


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

    @Column(nullable = false)
    private String username;

//
//    @Column(columnDefinition = "integer default 0")
//    private int viewcount;

    @OneToMany(mappedBy = "mainPost", cascade = CascadeType.REMOVE) //mappedby => 참조엔티티 속성명, cascade => 질문 삭제하면 그 예하 묶인 놈들 싸그리 삭제함.
    private List<MainPostComent> mainPostComents; //1:N방식이니까. 부모엔티티가 자식엔티티를 여러개 가질 수 있어


    @Builder
    public MainPost(String title, String content, String username){
        this.title = title;
        this.content = content;
        this.username = username;
    }


    public void update(String title, String content, String username){
        this.title = title;
        this.content = content;
        this.username = username;
    }


}
