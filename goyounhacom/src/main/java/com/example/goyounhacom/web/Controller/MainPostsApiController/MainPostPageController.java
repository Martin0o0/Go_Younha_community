package com.example.goyounhacom.web.Controller.MainPostsApiController;

import com.example.goyounhacom.Service.MainPostsService;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.domain.MainPosts.MainPostRepository;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mainpost")
public class MainPostPageController {
    private final MainPostsService mainPostsService;


    @GetMapping("/mainlist")
    public String mainpost(Model model){
        List<MainPostGetDto> list = mainPostsService.findByAll();
        model.addAttribute("main_post", list);
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
