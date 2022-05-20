package com.example.goyounhacom.domain.MainPosts;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommentRepository extends JpaRepository<Recomment, Long> {
    Boolean existsByMainpostid(Long id);

    List<Recomment> findAllByMainpostid(Long id, Sort sort);

}
