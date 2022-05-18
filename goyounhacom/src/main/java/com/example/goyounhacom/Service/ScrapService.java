package com.example.goyounhacom.Service;


import com.example.goyounhacom.domain.MainPosts.MainPost;
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

    @Transactional(readOnly = true)
    public Page<Scrap> getList(Long userid, Pageable pageable){
        Page<Scrap> scraps = scrapRepository.findAllByUserId(userid, pageable);
        return scraps;
    }

    @Transactional
    public void save(String username, Long postid){
         User user = userService.getbyUsername(username);
         MainPost mainPost = mainPostsService.getMainPostId(postid);
         Scrap scrap = new Scrap(user, mainPost);
         scrapRepository.save(scrap);
    }

    @Transactional
    public void delete(String username, Long postid){
        User user = userService.getbyUsername(username);
        MainPost mainPost = mainPostsService.getMainPostId(postid);
        Scrap scrap = new Scrap(user, mainPost);
        scrapRepository.delete(scrap);
    }


}
