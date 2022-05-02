package com.example.goyounhacom.Service;

import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.Users.UserRepository;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserSaveDto;
import com.example.goyounhacom.web.Dto.UserDto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(UserSaveDto userSaveDto) {
        if (userRepository.existsByUsername(userSaveDto.getUsername())) {
            return (long) -1;
        }
        else {
            String hashpw = passwordEncoder.encode(userSaveDto.getPassword());
            userSaveDto.setPassword(hashpw);
            return userRepository.save(userSaveDto.toEntity()).getId();
        }
    }

    @Transactional
    public Long update(Long id, UserUpdateDto userUpdateDto) { //유저정보 업데이트
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없음"));
        user.update(passwordEncoder.encode(userUpdateDto.getPassword()), userUpdateDto.getEmail(), userUpdateDto.getNickname());
        return user.getId();
    }

    @Transactional
    public Long updateUser(UserUpdateDto userUpdateDto, @AuthenticationPrincipal PrincipalDatails principalDatails, String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없음"));
        user.update(passwordEncoder.encode(userUpdateDto.getPassword()), userUpdateDto.getEmail(), userUpdateDto.getEmail());
        principalDatails.setUser(user);
        return user.getId();
    }


    @Transactional
    public List<UserGetDto> findByAll() { //전체조회
        return userRepository.findAll().stream().map(dto -> new UserGetDto(dto)).collect(Collectors.toList());
    }


    @Transactional
    public UserGetDto findbyid(Long No) { //회원번호로 조회
        User entity = userRepository.findById(No).orElseThrow(() -> new IllegalArgumentException("해당하는 유저는 없습니다. No : " + No));

        return new UserGetDto(entity);
    }

    @Transactional
    public UserGetDto findbyusername(String userid) {//회원아이디로 조회
        User entity = userRepository.findByUsername(userid).orElseThrow(() -> new IllegalArgumentException("해당하는 유저 아이디는 없습니다. " + userid));
        return new UserGetDto(entity);
    }

    @Transactional
    public User getbyUsername(String username){
        User entity = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("해당하는 유저 아이디는 없습니다. " + username));
        return entity;
    }

    @Transactional
    public String deletebyid(Long No) { //회원번호로 삭제
        User user = userRepository.findById(No).orElseThrow(() -> new IllegalArgumentException("해당하는 회원번호가 없다. 삭제할 수 없음."));
        userRepository.delete(user);
        return "회원번호" + No + "는 삭제되었습니다.";
    }



}
