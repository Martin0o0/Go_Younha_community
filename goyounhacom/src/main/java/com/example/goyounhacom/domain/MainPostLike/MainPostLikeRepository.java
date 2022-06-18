package com.example.goyounhacom.domain.MainPostLike;

import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MainPostLikeRepository extends JpaRepository<MainPostLike, Long> {
    Boolean existsByUserIdAndMainPostId(Long userid, Long mainPostid);
    Optional<Long> countByMainPostId(Long mainPost);

    Optional<MainPostLike> findByUserIdAndMainPostId(Long userid, Long mainPostid);

    List<MainPostLike> findAllByMainPostId(Long postid);

    List<MainPostLike> findAllByUserId(Long userid);

    Boolean existsByMainPostId(Long mainPostid);
}
