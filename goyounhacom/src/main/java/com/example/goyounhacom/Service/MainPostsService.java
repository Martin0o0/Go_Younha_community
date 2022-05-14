package com.example.goyounhacom.Service;


import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostRepository;
import com.example.goyounhacom.domain.Photo.PhotoRepository;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsGetDto;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostSaveDto;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MainPostsService {
    private final MainPostRepository mainPostRepository;
    private final PhotoRepository photoRepository;
    private final FileService fileService;

    @Transactional
    public Long save(MainPostSaveDto mainPostSaveDto) {
        return mainPostRepository.save(mainPostSaveDto.toEntity()).getId();
        //반환값은 HelloPost가 된다. getId를 통해 등록된 글 번호를 가져오자.
    }


    @Transactional
    public List<MainPostGetDto> findByAll() {
        return mainPostRepository.findAll().stream().map(dto -> new MainPostGetDto(dto)).collect(Collectors.toList());
    }

    @Transactional
    public MainPostGetDto getMainpost(Long id) {
        MainPost posts = mainPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없다."));

        return new MainPostGetDto(posts);
    }

    @Transactional
    public Long save(String title, String content, Long fileId, User user) {
        MainPostSaveDto mainPostSaveDto = new MainPostSaveDto(title, content, fileId ,user);
        return mainPostRepository.save(mainPostSaveDto.toEntity()).getId();
        //반환값은 HelloPost가 된다. getId를 통해 등록된 글 번호를 가져오자.
    }

   @Transactional
    public void like(Long id, User user){
        MainPost mainpost = mainPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없다."));
        mainpost.getLike().add(user);
        this.mainPostRepository.save(mainpost);
    }

    @Transactional
    public void deleteLike(Long id, User user){ //좋아요 삭제.
        MainPost mainpost = mainPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없다."));
        mainpost.getLike().remove(user);
        this.mainPostRepository.save(mainpost);
    }

    @Transactional
    public Long modify(Long id, String title, String content) {
        MainPost post = mainPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 없다. No : " + id));
        post.update(title, content);
        return id;
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Page<MainPost> pageList(Pageable pageable){ //page<T>로 지정하면 바드시 파라미터로 pageable한것으로 해주어야 한다고 함.
        Page<MainPost> list = mainPostRepository.findAll(pageable);
        return list;
    }

    @Transactional
    public Long delete(Long id){
        MainPost posts = mainPostRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없다. 삭제할 수 없음."));
        if(posts.getFileId() != null && photoRepository.existsById(posts.getFileId())){
            fileService.DeleteFile(posts.getFileId());
            log.info("삭제된 파일 ID : {} ", posts.getFileId());
        }
        mainPostRepository.delete(posts);
        return id;
    }

    @Transactional
    public void updateviewcount(Long id){
        mainPostRepository.updateviewcount(id);
    }

    @Transactional
    public Page<MainPost> search(String title, String content, Pageable pageable){
        Page<MainPost> list = mainPostRepository.findByTitleContainingOrContentContaining(title, content, pageable);
        return list;
    }
}
