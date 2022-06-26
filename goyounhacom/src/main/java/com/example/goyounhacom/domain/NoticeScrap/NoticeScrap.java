package com.example.goyounhacom.domain.NoticeScrap;


import com.example.goyounhacom.domain.Infomation.Notice;
import com.example.goyounhacom.domain.Users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class NoticeScrap {
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
    private Notice notice;

    @Builder
    public NoticeScrap(User user, Notice notice){
        this.user = user;
        this.notice = notice;
    }
}
