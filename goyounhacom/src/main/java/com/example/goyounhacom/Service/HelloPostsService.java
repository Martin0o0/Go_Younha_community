package com.example.goyounhacom.Service;


import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.HelloPosts.HelloPostRepository;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsGetDto;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HelloPostsService {

    private final HelloPostRepository helloPostRepository;

    @Transactional
    public Long save(HelloPostsSaveDto helloPostsSaveDto){
        return helloPostRepository.save(helloPostsSaveDto.toEntity()).getId();
        //반환값은 HelloPost가 된다. getId를 통해 등록된 글 번호를 가져오자.
    }

    @Transactional
    public Long update(Long id, HelloPostsUpdateDto helloPostsUpdateDto){
        HelloPost posts = helloPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 없다. No : " + id));
        posts.update(helloPostsUpdateDto.getTitle(), helloPostsUpdateDto.getContent(), helloPostsUpdateDto.getUserId());
        return id;
    }


    @Transactional
    public HelloPostsGetDto findbyid(Long No){
        HelloPost entity = helloPostRepository.findById(No).orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 없습니다. No : " + No));

        return new HelloPostsGetDto(entity);
    }

    @Transactional
    public List<HelloPostsGetDto> findbyuserid(String userid){
        List<HelloPostsGetDto> list = helloPostRepository.findByUserId(userid).stream().map(dto -> new HelloPostsGetDto(dto)).collect(Collectors.toList());
        return list;
    }


    @Transactional
    public Long deletebyid(Long No){
        HelloPost posts = helloPostRepository.findById(No).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없다. 삭제할 수 없음."));
        helloPostRepository.delete(posts);
        return No;
    }



    @Transactional
    public List<HelloPostsGetDto> findByAll(){
        return  helloPostRepository.findAll().stream().map(dto -> new HelloPostsGetDto(dto)).collect(Collectors.toList());
    }



    @Transactional
    public List<HelloPostsGetDto> findbysearchlist(String key){
        List<HelloPostsGetDto> list = helloPostRepository.findByTitleContaining(key).stream().map(dto -> new HelloPostsGetDto(dto)).collect(Collectors.toList());
        return list;
    }
}
