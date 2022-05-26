package com.example.goyounhacom.Service;


import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.HelloPosts.HelloPostRepository;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsGetDto;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsUpdateDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Long save(String content, User user){
       HelloPostsSaveDto helloPostsSaveDto = new HelloPostsSaveDto(content, user);
       return helloPostRepository.save(helloPostsSaveDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, HelloPostsUpdateDto helloPostsUpdateDto){
        HelloPost posts = helloPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 없다. No : " + id));
        posts.update(helloPostsUpdateDto.getContent());
        return id;
    }

    @Transactional
    public Long modify(Long id, String content){
        HelloPost post = helloPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 없다. No : " + id));
        post.update(content);
        return id;
    }


    @Transactional
    public HelloPostsGetDto findbyid(Long No){
        HelloPost entity = helloPostRepository.findById(No).orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 없습니다. No : " + No));
        return new HelloPostsGetDto(entity);
    }

//    @Transactional
//    public List<HelloPostsGetDto> findbyuserid(String userid){
//        List<HelloPostsGetDto> list = helloPostRepository.findByUsername(userid).stream().map(dto -> new HelloPostsGetDto(dto)).collect(Collectors.toList());
//        return list;
//    }

    @Transactional
    public HelloPostsGetDto getHelloPosts(Long id) {
        HelloPost post= helloPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없다."));
        return new HelloPostsGetDto(post);
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

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Page<HelloPost> pageList(Pageable pageable){ //page<T>로 지정하면 바드시 파라미터로 pageable한것으로 해주어야 한다고 함.
        Page<HelloPost> list = helloPostRepository.findAll(pageable);
        return list;
    }




    @Transactional
    public List<HelloPostsGetDto> findbysearchlist(String key){
        List<HelloPostsGetDto> list = helloPostRepository.findByTitleContaining(key).stream().map(dto -> new HelloPostsGetDto(dto)).collect(Collectors.toList());
        return list;
    }
//
//    @Transactional
//    public Long updateviewcount(Long id){
//        return helloPostRepository.updateviewcount(id);
//    }
}
