package com.example.goyounhacom.web.Controller.ScrapController;


import com.example.goyounhacom.Service.ScrapService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.Scrap.Scrap;
import com.example.goyounhacom.domain.Scrap.ScrapRepository;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/user")
public class ScrapController {
    private final UserService userService;
    private final ScrapService scrapService;


    @GetMapping("/scrap/{userid}")
    public List<MainPostGetDto> scrapList(@PathVariable Long userid){
        log.info("회원번호 : {}", userid);
        List<Scrap> list = scrapService.getList(userid);

        List<MainPostGetDto> postlist= new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            postlist.add(new MainPostGetDto(list.get(i).getMainPost()));
        }
        return postlist;
    }

    @PostMapping("/scrap/{username}")
    public void savepost(@PathVariable String username, @RequestParam Long postid) {
        scrapService.save(username, postid);
        log.info("{}의 스크랩에 등록된  게시글 번호 : {}", username, postid);
    }

    @DeleteMapping("/scrap/{username}")
    public void deletepost(@PathVariable String username, @RequestParam Long postid){
        scrapService.delete(username, postid);
        log.info("{}의 스크랩에 제거된 게시글 번호 : {} ", username, postid);
    }
}
