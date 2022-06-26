package com.example.goyounhacom.domain.NoticeScrap;

import com.example.goyounhacom.domain.Scrap.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeScrapRepository extends JpaRepository<NoticeScrap, Long> {

    Page<NoticeScrap>  findAllByUserId(Long userid, Pageable pageable);

    List<NoticeScrap> findAllByNoticeId(Long noticeid);

    Boolean existsByUserId(Long userid);

    Boolean existsByNoticeId(Long id);

    Boolean existsByUserIdAndNoticeId(Long userid, Long noticeid);

    NoticeScrap findByUserIdAndNoticeId(Long userid, Long noticeid);
}
