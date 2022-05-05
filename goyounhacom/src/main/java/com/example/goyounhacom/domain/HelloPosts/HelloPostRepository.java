package com.example.goyounhacom.domain.HelloPosts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface HelloPostRepository extends JpaRepository<HelloPost, Long> {

    List<HelloPost> findByTitleContaining(String keyword); //Json객체로 반환.


//    @Modifying
//    @Query("update HelloPost as p set p.viewcount = p.viewcount + 1 where p.id = :id") //조회수 증가
//    Long updateviewcount(Long id);
}
