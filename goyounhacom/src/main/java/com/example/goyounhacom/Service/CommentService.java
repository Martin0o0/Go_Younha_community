package com.example.goyounhacom.Service;

import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostComment;
import com.example.goyounhacom.domain.MainPosts.MainPostCommentRepository;
import com.example.goyounhacom.domain.MainPosts.MainPostRepository;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentGetDto;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final MainPostCommentRepository mainPostCommentRepository;
    private final MainPostRepository mainPostRepository;



    @Transactional
    public Long saveComment(Long id, String content, User user) {
        MainPost mainPost = mainPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 없다. No : " + id));
        CommentSaveDto commentSaveDto = new CommentSaveDto(content, mainPost, user);

        return mainPostCommentRepository.save(commentSaveDto.toEntity()).getId();
    }

    @Transactional
    public CommentGetDto getComment(Long id){
        MainPostComment comment = mainPostCommentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당하는 번호의 댓글이 없다."));
        return new CommentGetDto(comment);
    }

    @Transactional
    public Long modify(Long id, String content) {
        MainPostComment comment = mainPostCommentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 댓글이 없다."));
        log.info("답변 : {} \n" , comment.getContent());
        comment.update(content);
        log.info("업데이트 후 답변 : {} ", comment.getContent());
        return id;
    }

    @Transactional
    public Long delete(Long id){
        MainPostComment comment = mainPostCommentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없다. 삭제할 수 없음."));
        mainPostCommentRepository.delete(comment);
        return id;
    }

    @Transactional
    public void like(Long id, User user){
        MainPostComment mainPostComment = mainPostCommentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없다."));
        mainPostComment.getLike().add(user);
        this.mainPostCommentRepository.save(mainPostComment);
    }


}
