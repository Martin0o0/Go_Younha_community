package com.example.goyounhacom.Config;

import com.example.goyounhacom.domain.Users.Role;
import com.example.goyounhacom.domain.Users.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
@Slf4j
@ToString
@Getter
@Builder
public class OAuthAttributes {


    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String nickname;
    private String email;
    private Role role;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        /* 구글인지 네이버인지 카카오인지 구분하기 위한 메소드 (ofNaver, ofKaKao) */

        if("naver".equals(registrationId) == true){
            log.info("일단 of함수 진입");
            return ofNaver("id", attributes); //네이버의 경우 provider의 값이 "id"이다.
        }
        else{
            return ofGoogle(userNameAttributeName, attributes);
        }
    }



    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder().username((String) attributes.get("email")).email((String) attributes.get("email")).nickname((String) attributes.get("name")).attributes(attributes).nameAttributeKey(userNameAttributeName).build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        log.info("Ofnaver까지 옴");
        Map<String, Object> response = (Map<String, Object>) attributes.get("response"); //response의 하위 값들을 맵으로 받아옴.
        return OAuthAttributes.builder().username((String) response.get("email")).email((String) response.get("email")).nickname((String) response.get("name")).attributes(response).nameAttributeKey(userNameAttributeName).build();
    }



}
