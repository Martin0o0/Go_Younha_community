package com.example.goyounhacom.Service;

import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostCommentRepository;
import com.example.goyounhacom.domain.MainPosts.MainPostRepository;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final MainPostCommentRepository mainPostCommentRepository;
    private final MainPostRepository mainPostRepository;

    @Transactional
    public Long saveComment(Long id, String username, String content) {
        MainPost mainPost = mainPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 없다. No : " + id));
        CommentSaveDto commentSaveDto = new CommentSaveDto(content, username, mainPost);
        return mainPostCommentRepository.save(commentSaveDto.toEntity()).getId();

    }

}
