package com.example.goyounhacom.Service;


import com.example.goyounhacom.domain.Users.UserRepository;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.UserDto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long save(UserSaveDto userSaveDto){
        return userRepository.save(userSaveDto.toEntity()).getId();
    }
}
