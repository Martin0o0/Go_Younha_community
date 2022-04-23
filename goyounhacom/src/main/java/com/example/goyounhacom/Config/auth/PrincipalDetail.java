package com.example.goyounhacom.Config.auth;

import com.example.goyounhacom.domain.Users.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


@NoArgsConstructor
@AllArgsConstructor
public class PrincipalDetail implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> user.getRoleKey());
        return collection;
    }


    @Override //pw
    public String getPassword() {
        return user.getPassword();
    }

    @Override //userid
    public String getUsername() {
        return user.getUserid();
    }

    public String getEmail(){
        return user.getEmail();
    }

   public void setUser(User user){
        this.user = user; //정보 변경 후 세션 변경 후 유지.

   }

    public String getNickname(){
        return user.getNickname();
    }

    public Long getId(){
        return user.getId();
    }

    @Override //계정이 만료되었는가?
    public boolean isAccountNonExpired() {
        return true; //만료안됨.
    }

    @Override
    public boolean isAccountNonLocked() { //계정이 잠겼는가?
        return true; //계정 안잠김.
    }

    @Override
    public boolean isCredentialsNonExpired() { //패스워드가 만료되었는가?
        return true; //안잠김
    }

    @Override
    public boolean isEnabled() { //계정 활성임?
        return true;  //활성화임.
    }
}
