package com.example.goyounhacom.Service;


import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.Scrap.Scrap;
import com.example.goyounhacom.domain.Scrap.ScrapRepository;
import com.example.goyounhacom.domain.Users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final UserService userService;
    private final MainPostsService mainPostsService;

    public List<Scrap> getList(Long userid){
        List<Scrap> scraps = scrapRepository.findAllByUser(userid);
        return scraps;
    }

    @Transactional
    public void save(String username, Long postid){
         User user = userService.getbyUsername(username);
         MainPost mainPost = mainPostsService.getMainPostId(postid);
         Scrap scrap = new Scrap(user, mainPost);
         scrapRepository.save(scrap);
    }

}
