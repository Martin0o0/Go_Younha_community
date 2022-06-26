package com.example.goyounhacom.Service;

import com.example.goyounhacom.Config.OAuthAttributes;
import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.domain.Users.Role;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.Users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class customOAuth2UserService implements OAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncode;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService(); //로그인한 유저가 DB에 저장되어 있는지 확인하는 서비스단.
        OAuth2User oAuth2User = delegate.loadUser(userRequest);/* OAuth2 서비스 id 구분코드 ( 구글, 카카오, 네이버 ) */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();         /* OAuth2 로그인 진행시 키가 되는 필드 값 (PK) (구글의 기본 코드는 "sub") */
        log.info("{}", registrationId);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();         /* OAuth2UserService */
        log.info("{}", userNameAttributeName);
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        log.info("{}", attributes.toString());
        User user = save(attributes);                /* 세션 정보를 저장하는 직렬화된 dto 클래스*/
        return new PrincipalDatails(user, attributes.getAttributes());

    }     /* 소셜로그인시 기존 회원이 존재하면 수정날짜 정보만 업데이트해 기존의 데이터는 그대로 보존 */

    /*
    registrationId : google
    userNameAttributeName : sub
    attributes.getAttributes() : {sub=XXX, name=XXX, given_name=XX(이름), family_name=X(성), picture=XXX, email=XXXX@gmail.com, email_verified=true, locale=ko}
     */


    private User save(OAuthAttributes attributes) {
        User user = userRepository.findByUsername(attributes.getEmail()).orElse(User.builder().username(attributes.getUsername()).password(passwordEncode.encode(UUID.randomUUID().toString())).email(attributes.getEmail()).isholics(false).nickname(attributes.getNickname()).role(Role.USER).build());
        return userRepository.save(user);
    }
}
