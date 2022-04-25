package com.example.goyounhacom.domain.MainPosts;

import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MainPostRepository extends JpaRepository<MainPost, Long> {
    List<MainPost> findByTitleContaining(String keyword); //Json객체로 반환.

//    @Modifying
//    @Query("update MainPost p set p.viewcount = p.viewcount + 1 where p.id = :id") //조회수 증가
//    int updateviewcount(int id);
}
