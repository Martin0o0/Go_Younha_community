package com.example.goyounhacom.domain.Scrap;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, ScrapId> {
    List<Scrap> findAllByUserId(Long userid);
}
