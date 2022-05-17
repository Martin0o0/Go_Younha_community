package com.example.goyounhacom.domain.Scrap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, ScrapId> {
    Page<Scrap> findAllByUserId(Long userid, Pageable pageable);
}
