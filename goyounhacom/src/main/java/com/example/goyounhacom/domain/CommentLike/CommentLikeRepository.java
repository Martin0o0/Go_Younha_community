package com.example.goyounhacom.domain.CommentLike;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Boolean existsByUserIdAndMainPostCommentId(Long userid, Long commentid);


    CommentLike findByUserIdAndMainPostCommentId(Long userid, Long commentid);


}
