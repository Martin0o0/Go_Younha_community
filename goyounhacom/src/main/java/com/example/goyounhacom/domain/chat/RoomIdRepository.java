package com.example.goyounhacom.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomIdRepository extends JpaRepository<RoomId, Long> {

    RoomId findByRoomnameAndUsername1(String roomname, String username1);

    List<RoomId> findByUsername1(String username);
}
