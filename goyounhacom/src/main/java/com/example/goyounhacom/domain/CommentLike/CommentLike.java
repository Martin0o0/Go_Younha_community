package com.example.goyounhacom.domain.CommentLike;


import com.example.goyounhacom.domain.MainPosts.MainPostComment;
import com.example.goyounhacom.domain.Users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Entity
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn
    private MainPostComment mainPostComment;


    @Builder
    public CommentLike(User user, MainPostComment mainPostComment){
        this.user = user;
        this.mainPostComment = mainPostComment;
    }

}
