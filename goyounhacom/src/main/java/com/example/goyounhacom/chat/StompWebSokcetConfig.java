package com.example.goyounhacom.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration //빈 등록
@EnableWebSocketMessageBroker //stomp를 사용하기 위한 어노테이션
public class StompWebSokcetConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chat").setAllowedOrigins("http://localhost:8090").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");  //클라이언트 쪽에서 보내오는 요청을 처리 (send)
        registry.enableSimpleBroker("/sub"); //해당 경로로 브로커를 설정. 브로커는 해당하는 경로로 구독하는 클라이언트에게 메시지를 전달하는 역할을 수행한다.
    }
}
