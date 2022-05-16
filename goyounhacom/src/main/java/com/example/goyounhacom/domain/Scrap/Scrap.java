package com.example.goyounhacom.domain.Scrap;

import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.Users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@IdClass(ScrapId.class)
@Getter
@NoArgsConstructor
@Entity
public class Scrap implements Serializable { //id가 두개인 경우이므로 id class를 만들어 주자.

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn //외래키 맵핑
    private User user;


    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn //외래키 맵핑
    private MainPost mainPost;

    @Builder
    public Scrap(User user, MainPost mainPost){
        this.user = user;
        this.mainPost = mainPost;
    }
}
