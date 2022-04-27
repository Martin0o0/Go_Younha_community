package com.example.goyounhacom.web.Controller.MainPostsApiController;

import com.example.goyounhacom.Service.MainPostsService;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mainpost")
public class MainPostPageController {
    private final MainPostsService mainPostsService;


    @GetMapping("/mainlist")
    public String mainpost(Model model, @PageableDefault(sort="id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<MainPost> list = mainPostsService.pageList(pageable); //읽기전용으로.

        model.addAttribute("main_post", list);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber()); //이전페이지 번호를 받아오고
        model.addAttribute("next", pageable.next().getPageNumber()); //다음 페이지 번호를 받아온다.
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        System.out.println(list.hasNext());
        System.out.println(list.hasPrevious());
        return "MainPost";
    }

    @GetMapping("/comment/{id}")
    public String mainpostcoment(@PathVariable Long id,  Model model){
        MainPostGetDto post = mainPostsService.getMainpost(id);
        model.addAttribute("main_post", post);
        return "MainPost_comment";
    }

    @GetMapping("/savepage")
    public String mainpostpage(){
        return "MainPost_save";
    }


    @PostMapping("/save")
    public String mainpostsave(@RequestParam String username, @RequestParam String title, @RequestParam String content){
        long savenum = mainPostsService.save(username, title, content);
        return "redirect:/mainpost/mainlist";

    }

}
