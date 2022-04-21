package com.example.goyounhacom.Service;


import com.example.goyounhacom.Config.auth.PrincipalDetail;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.Users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService  implements UserDetailsService {
    private final UserRepository userRepository;

    @Override //UserDetailsService를 받아 데이터베이스에 Userid가 존재하는지 확인하는 메소드.
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        User user = userRepository.findByUserid(userid).orElseThrow(() -> new UsernameNotFoundException("해당 이용자는 없다. : " + userid));
        return new PrincipalDetail(user); //시큐리티 세션에 유저 정보 저장.
    }
}
