package com.example.goyounhacom.domain.HelloPosts;


import com.example.goyounhacom.domain.baseTimeEntity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class HelloPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키, 자동증가
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String username;

//    @Column(columnDefinition = "integer default 0", nullable = true)
//    private Integer viewcount;

    @Builder
    public HelloPost(String title, String content, String username){
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
