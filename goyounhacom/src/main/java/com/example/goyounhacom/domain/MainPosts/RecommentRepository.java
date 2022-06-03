package com.example.goyounhacom.domain.MainPosts;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommentRepository extends JpaRepository<Recomment, Long> {
    Boolean existsByMainPostId(Long id);

    List<Recomment> findAllByMainPostId(Long id, Sort sort);

}
