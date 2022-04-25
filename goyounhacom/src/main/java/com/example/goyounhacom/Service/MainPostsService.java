package com.example.goyounhacom.Service;


import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostRepository;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsGetDto;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MainPostsService {
    private final MainPostRepository mainPostRepository;

    @Transactional
    public Long save(MainPostSaveDto mainPostSaveDto){
        return mainPostRepository.save(mainPostSaveDto.toEntity()).getId();
        //반환값은 HelloPost가 된다. getId를 통해 등록된 글 번호를 가져오자.
    }


    @Transactional
    public List<MainPostGetDto> findByAll(){
        return  mainPostRepository.findAll().stream().map(dto -> new MainPostGetDto(dto)).collect(Collectors.toList());
    }

    @Transactional
    public MainPostGetDto getMainpost(Long id){
        MainPost posts = mainPostRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없다."));
        return new MainPostGetDto(posts);
    }


}
