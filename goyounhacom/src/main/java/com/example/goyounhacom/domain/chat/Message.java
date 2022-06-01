package com.example.goyounhacom.domain.chat;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String writer;
    private String msg;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String roomid;

    @Builder
    public Message(String writer, String msg, String roomid) {
        this.writer = writer;
        this.msg = msg;
        this.roomid = roomid;
    }
}
