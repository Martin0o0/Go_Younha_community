package com.example.goyounhacom.domain.MainPosts;


import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.baseTimeEntity.BaseTimeEntity;
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
    private User user;

    @ManyToOne
    private MainPostComment mainPostComment;

    private Long mainpostid;


    @Builder
    public Recomment(String content, User user, MainPostComment mainPostComment, Long mainpostid){
        this.content = content;
        this.user = user;
        this.mainPostComment = mainPostComment;
        this.mainpostid = mainpostid;
    }


    @Override
    public String toString() {
        return "Recomment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", mainPostComment=" + mainPostComment +
                ", mainpostid=" + mainpostid +
                '}';
    }

    public void update(String content){
        this.content = content;
    }
}
