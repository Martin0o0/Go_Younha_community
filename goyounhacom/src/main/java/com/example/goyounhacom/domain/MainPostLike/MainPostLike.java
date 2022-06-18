package com.example.goyounhacom.domain.MainPostLike;

import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.Users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@NoArgsConstructor
@Entity
public class MainPostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference //순환참조 방지
    @JoinColumn //왜래키 맵핑
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn
    private MainPost mainPost;


    @Builder
    public MainPostLike(User user, MainPost mainPost){
        this.user = user;
        this.mainPost = mainPost;
    }

}
