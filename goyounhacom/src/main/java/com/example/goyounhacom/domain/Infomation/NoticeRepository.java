package com.example.goyounhacom.domain.Infomation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findTop3ByOrderByIdDesc(); //제일최근 3개 리스트만 받아오기.

}
