package com.example.goyounhacom.Config;

import com.example.goyounhacom.domain.Users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class PrincipalDatails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> user.getRoleKey());
        return collection;
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
