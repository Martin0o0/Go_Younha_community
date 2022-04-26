package com.example.goyounhacom.domain.MainPosts;

import com.example.goyounhacom.domain.baseTimeEntity.BaseTimeEntity;
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

    @Column(nullable = false)
    private String username;

    @ManyToOne
    private MainPost mainPost; //자식이니까 N:1방식이다.

    @Builder
    public MainPostComment(String title, String content, String username, MainPost mainPost){
        this.content = content;
        this.username = username;
        this.mainPost = mainPost;
    }
}
