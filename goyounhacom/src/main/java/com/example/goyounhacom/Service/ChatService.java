package com.example.goyounhacom.Service;

import com.example.goyounhacom.domain.chat.Message;
import com.example.goyounhacom.domain.chat.MessageRepository;
import com.example.goyounhacom.domain.chat.RoomId;
import com.example.goyounhacom.domain.chat.RoomIdRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final RoomIdRepository roomIdRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public String makeRoom(String username1, String username2, String roomname){
        RoomId roomid = new RoomId(username1, username2, roomname);
        return roomIdRepository.save(roomid).getRoomname();
    }

    @Transactional
    public RoomId findByroomname(String roomname, String username1){
        return roomIdRepository.findByRoomnameAndUsername1(roomname, username1);
    }


    @Transactional
    public List<LocalDate> finddistict(String roomid) {
        return messageRepository.findDistinctByRoomid(roomid);
    }

    @Transactional
    public Long messageinfo(String writer, String msg, String roomid){
        Message message = new Message(writer, msg, roomid);
        return messageRepository.save(message).getId();
    }

    @Transactional
    public List<RoomId> findListuserid(String username){
        return roomIdRepository.findByUsername1(username);
    }


    @Transactional
    public List<Message> findListMsg(String roomname){
        return messageRepository.findByRoomid(roomname);
    }

    @Transactional
    public List<Message> findByLocalDate(LocalDate localDate){
        return messageRepository.findAllByLocalDate(localDate);
    }

}
