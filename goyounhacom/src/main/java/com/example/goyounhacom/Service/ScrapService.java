package com.example.goyounhacom.Service;


import com.example.goyounhacom.domain.Infomation.Notice;
import com.example.goyounhacom.domain.Infomation.NoticeRepository;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.NoticeScrap.NoticeScrap;
import com.example.goyounhacom.domain.NoticeScrap.NoticeScrapRepository;
import com.example.goyounhacom.domain.Scrap.Scrap;
import com.example.goyounhacom.domain.Scrap.ScrapRepository;
import com.example.goyounhacom.domain.Users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final UserService userService;
    private final MainPostsService mainPostsService;
    private final NoticeService noticeService;
    private final NoticeScrapRepository noticeScrapRepository;

    @Transactional(readOnly = true)
    public Page<Scrap> getList(Long userid, Pageable pageable){
        Page<Scrap> scraps = scrapRepository.findAllByUserId(userid, pageable);
        return scraps;
    }

    @Transactional(readOnly = true)
    public Page<NoticeScrap> getNoticeList(Long userid, Pageable pageable){
        Page<NoticeScrap> scraps = noticeScrapRepository.findAllByUserId(userid, pageable);
        return scraps;
    }

    @Transactional
    public void save(String username, Long postid){
         User user = userService.getbyUsername(username);
         MainPost mainPost = mainPostsService.getMainPostId(postid);
         if(scrapRepository.existsByUserIdAndMainPostId(user.getId(), postid) == false) {
             Scrap scrap = new Scrap(user, mainPost);
             scrapRepository.save(scrap);
             log.info("{}", "게시글 스크랩 저장됨");
         }else{
             Scrap scrap = scrapRepository.findByUserIdAndMainPostId(user.getId(), postid);
             scrapRepository.delete(scrap);
             log.info("{}", "게시글 스크랩 삭제됨");
         }
    }

    @Transactional
    public void noticesave(String username, Long postid){
        User user = userService.getbyUsername(username);
        Notice notice = noticeService.findByid(postid);
        if(noticeScrapRepository.existsByUserIdAndNoticeId(user.getId(),postid) == false){
            NoticeScrap noticeScrap = new NoticeScrap(user, notice);
            noticeScrapRepository.save(noticeScrap);
            log.info("공지사항 스크랩 됨}");
        }else{
            NoticeScrap noticeScrap = noticeScrapRepository.findByUserIdAndNoticeId(user.getId(), postid);
            noticeScrapRepository.delete(noticeScrap);
            log.info("공지사항 스크랩 됨");
        }
    }


    @Transactional
    public void delete(String username, Long postid){
        User user = userService.getbyUsername(username);
        Scrap scrap = scrapRepository.findByUserIdAndMainPostId(user.getId(), postid);
        scrapRepository.delete(scrap);
        log.info("{}의 {} 게시물 스크랩 삭제 완료", username, postid );
    }



    @Transactional
    public void deletecreap(String username, Long noticeid){
        User user = userService.getbyUsername(username);
        NoticeScrap noticeScrap = noticeScrapRepository.findByUserIdAndNoticeId(user.getId(), noticeid);
        noticeScrapRepository.delete(noticeScrap);
        log.info("{}의 {} 공지사항 스크랩 삭제 완료");
    }




    public Boolean findByScrap(Long userid, Long mainPostid){
        return scrapRepository.existsByUserIdAndMainPostId(userid, mainPostid);
    }

    public Boolean findByNotice(Long userid, Long noticeid){
        return noticeScrapRepository.existsByUserIdAndNoticeId(userid, noticeid);
    }



}
