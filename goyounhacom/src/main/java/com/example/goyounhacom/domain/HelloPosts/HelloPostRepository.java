package com.example.goyounhacom.domain.HelloPosts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HelloPostRepository extends JpaRepository<HelloPost, Long> {

    List<HelloPost> findByTitleContaining(String keyword); //Json객체로 반환.

    List<HelloPost> findByUserId(String userid);
}
