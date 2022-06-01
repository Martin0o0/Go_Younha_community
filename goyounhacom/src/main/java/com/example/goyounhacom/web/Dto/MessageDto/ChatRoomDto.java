package com.example.goyounhacom.web.Dto.MessageDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
public class ChatRoomDto {
    private String roomid;
    //private Set<WebSocketSession> sessions = new HashSet<>(); //세션을 셋으로 정의하여 오직 하나의 세션만 담을 수 있도록 함.

    public static ChatRoomDto create(String name){
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.roomid = UUID.randomUUID().toString();
        return chatRoomDto;
    }


}
