package com.example.goyounhacom.domain.MainPosts;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MainPostRepository extends JpaRepository<MainPost, Long> {
    //List<MainPost> findByTitleContainingOrContentContaining(String keyword); //Json객체로 반환.

    Page<MainPost> findByTitleContainingOrContentContaining(String title, String content,  Pageable pageable);

    List<MainPost> findByUser(User user);

    Optional<MainPost> findById(Long postid);

    @Modifying
    @Query("update MainPost p set p.viewcount = p.viewcount + 1 where p.id = :id") //조회수 증가
    int updateviewcount(Long id);



}
