package com.example.goyounhacom.chat;

import com.example.goyounhacom.web.Dto.MessageDto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StompContoller {
    private final SimpMessagingTemplate template; //특정 브로커로 메시지를 전달해야함.

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter") //발행 요청을 하면,
    public void enter(ChatMessageDto message){
        message.setMessage(message.getWritter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto message){
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message); //이쪽으로 메시지가 전달된다.
    }

    @MessageMapping("/chat/disconnect")
    public void disconnect(ChatMessageDto message){
        log.info("퇴장함수 실행");
        message.setMessage(message.getWritter() + "님이 채팅방에 퇴장하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
