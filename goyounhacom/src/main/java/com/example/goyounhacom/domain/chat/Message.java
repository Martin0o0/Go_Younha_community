package com.example.goyounhacom.domain.chat;


import com.example.goyounhacom.domain.baseTimeEntity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor

@Getter
@EntityListeners(AuditingEntityListener.class) //기능 추가
@Entity
public class Message  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String writer;
    private String msg;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String roomid;


    @CreatedDate
    @Column(updatable = false)
    private LocalDate localDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalTime localTime;

    @Builder
    public Message(String writer, String msg, String roomid) {
        this.writer = writer;
        this.msg = msg;
        this.roomid = roomid;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", msg='" + msg + '\'' +
                ", roomid='" + roomid + '\'' +
                ", localDate=" + localDate +
                ", localTime=" + localTime +
                '}';
    }
}
