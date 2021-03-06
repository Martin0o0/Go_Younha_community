package com.example.goyounhacom.domain.chat;


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
@Entity
public class RoomId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username1;
    private String username2;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String roomname;



    @Builder
    public RoomId(String username1, String username2,String roomname){
        this.username1 = username1;
        this.username2 = username2;
        this.roomname = roomname;
    }
}
