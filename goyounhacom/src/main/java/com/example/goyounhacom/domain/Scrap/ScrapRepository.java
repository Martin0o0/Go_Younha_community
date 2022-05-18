package com.example.goyounhacom.domain.Scrap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;


public interface ScrapRepository extends JpaRepository<Scrap, ScrapId> {
    Page<Scrap> findAllByUserId(Long userid, Pageable pageable);

    List<Scrap> findAllByMainPostId(Long postid);

    Boolean existsByUserId(Long userid);

    Boolean existsByMainPostId(Long postid);
}
