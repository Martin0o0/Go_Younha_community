package com.example.goyounhacom.Service;


import com.example.goyounhacom.domain.MainPosts.MainPostComment;
import com.example.goyounhacom.domain.MainPosts.MainPostCommentRepository;
import com.example.goyounhacom.domain.MainPosts.Recomment;
import com.example.goyounhacom.domain.MainPosts.RecommentRepository;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.Users.UserRepository;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.RecommentGetDto;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.RecommentSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.events.Comment;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecommentService {
    private final RecommentRepository recommentRepository;
    private final MainPostCommentRepository mainPostCommentRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    @Transactional
    public Long saveRecomment(Long commentid, String content, User user){
        MainPostComment mainPostComment = mainPostCommentRepository.findById(commentid).orElseThrow(() -> new IllegalArgumentException("해당하는 댓글이 없다. No : " + commentid));
        Long postid = mainPostComment.getMainPost().getId();
        Recomment recomment = new Recomment(content, user, mainPostComment, postid);
        return recommentRepository.save(recomment).getId();
    }

    @Transactional
    public Boolean existbymainpostid(Long id){
        return recommentRepository.existsByMainpostid(id);
    }

    @Transactional
    public List<Recomment> findallbymainpostid(Long id){
        Sort sort = Sort.by(Sort.Direction.DESC, "mainPostComment");
        return recommentRepository.findAllByMainpostid(id, sort);
    }

    @Transactional
    public RecommentGetDto getRecomment(Long id){
        Recomment recomment = recommentRepository.getById(id);
        return new RecommentGetDto(recomment);
    }

    @Transactional
    public Long modify(Long id, String content){
        Recomment recomment = recommentRepository.getById(id);
        recomment.update(content);
        return id;
    }

    @Transactional
    public Long delete(Long id){
        Recomment recomment = recommentRepository.getById(id);
        recommentRepository.delete(recomment);
        return id;
    }
}
