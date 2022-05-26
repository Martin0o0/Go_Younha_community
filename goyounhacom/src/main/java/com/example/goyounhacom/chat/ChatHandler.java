package com.example.goyounhacom.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ChatHandler  extends TextWebSocketHandler {

    private List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload(); //전송되는 데이터를 의미함.
        log.info("payload 데이터 : " + payload);
        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }
    }

    @Override //클라이언트가 접속 시 호출되는 메서드
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        list.remove(session);
    }




}
