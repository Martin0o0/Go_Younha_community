package com.example.goyounhacom.Service;


import com.example.goyounhacom.domain.HelloPosts.HelloPostRepository;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class HelloPostsService {

    private final HelloPostRepository helloPostRepository;

    @Transactional
    public Long save(HelloPostsSaveDto helloPostsSaveDto){
        return helloPostRepository.save(helloPostsSaveDto.toEntity()).getId();
        //반환값은 HelloPost가 된다. getId를 통해 등록된 글 번호를 가져오자.
    }
}
