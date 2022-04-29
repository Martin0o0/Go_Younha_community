package com.example.goyounhacom.Service;

import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostCommentRepository;
import com.example.goyounhacom.domain.MainPosts.MainPostRepository;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import com.example.goyounhacom.web.Dto.UserDto.UserGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

}
