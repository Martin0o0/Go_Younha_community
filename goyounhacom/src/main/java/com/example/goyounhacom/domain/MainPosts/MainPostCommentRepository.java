package com.example.goyounhacom.domain.MainPosts;

import com.example.goyounhacom.domain.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainPostCommentRepository extends JpaRepository<MainPostComment, Long> {
    Boolean existsByUser(User user);

    List<MainPostComment> findByUser(User user);
}
