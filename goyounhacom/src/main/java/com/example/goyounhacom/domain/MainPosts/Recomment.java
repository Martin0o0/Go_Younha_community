package com.example.goyounhacom.domain.MainPosts;


import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.baseTimeEntity.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Recomment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn
    @JsonBackReference //순환참조 방지.
    private User user;

    @ManyToOne
    @JoinColumn
    @JsonBackReference //순환참조 방지.
    private MainPostComment mainPostComment;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private MainPost mainPost;



    @Builder
    public Recomment(String content, User user, MainPostComment mainPostComment, MainPost mainPost){
        this.content = content;
        this.user = user;
        this.mainPostComment = mainPostComment;
        this.mainPost = mainPost;
    }


    @Override
    public String toString() {
        return "Recomment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", mainPostComment=" + mainPostComment +
                ", mainPost=" + mainPost +
                '}';
    }

    public void update(String content){
        this.content = content;
    }
}
