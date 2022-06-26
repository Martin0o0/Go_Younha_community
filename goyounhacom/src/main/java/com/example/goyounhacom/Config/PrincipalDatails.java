package com.example.goyounhacom.Config;

import com.example.goyounhacom.domain.Users.User;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@ToString
@RequiredArgsConstructor
public class PrincipalDatails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes; //Oauth 방식

    public PrincipalDatails(User user){
        this.user = user;
    } //form방식

    public PrincipalDatails(User user, Map<String, Object> attributes) {
        //PrincipalOauth2UserService 참고
        this.user = user;
        this.attributes = attributes;
    }

    @Override //oauthe 방식
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> user.getRoleKey()); //유저의 Role을 담음.
        return collection;
    }

    @Override
    public String getName() {
        return this.toString();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }


    public User getUser(){
        return this.getUser();
    }

    public String getEmail(){
        return user.getEmail();
    }

    public String getNickname(){
        return user.getNickname();
    }

    public void setUser(User user){
        this.user = user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있는지 (true: 잠겨있지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //패스워드가 만료되지 않았는지 (true: 만료되지 않음)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화되어 있는지 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }


}
