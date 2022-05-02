package com.example.goyounhacom.Service;


import com.example.goyounhacom.Config.PrincipalDatails;
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
        User user = userRepository.findByUsername(userid).orElseThrow(() -> new UsernameNotFoundException("해당 이용자는 없다. : " + userid));
//        return org.springframework.security.core.userdetails.User
//                .builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .roles(user.getRole().name())
//                .build();
        return new PrincipalDatails(user);
    }
}
