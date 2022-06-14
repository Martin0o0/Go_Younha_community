package com.example.goyounhacom.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRoomid(String roomname);

    @Query("select distinct p.localDate from Message p")
    List<LocalDate> findDistinctByRoomid(String roomid);

    List<Message> findAllByLocalDate(LocalDate localDate);

}