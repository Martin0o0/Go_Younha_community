package com.example.goyounhacom.web.Controller.MainPostsApiController;

import com.example.goyounhacom.Service.MainPostsService;
import com.example.goyounhacom.domain.MainPosts.MainPost;
import com.example.goyounhacom.web.Dto.MainPostDto.CommentDto.CommentSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mainpost")
public class MainPostPageController {
    private final MainPostsService mainPostsService;


    @GetMapping("/mainlist")
    public String mainpost(Model model, @PageableDefault(sort="id", direction = Sort.Direction.DESC, size = 5) Pageable pageable){
        Page<MainPost> list = mainPostsService.pageList(pageable); //읽기전용으로.

        model.addAttribute("main_post", list);
        return "MainPost";
    }

    @GetMapping("/comment/{id}")
    public String mainpostcoment(@PathVariable Long id, Model model, CommentSaveDto commentSaveDto){
        MainPostGetDto post = mainPostsService.getMainpost(id);
        model.addAttribute("main_post", post);
        return "MainPost_comment";
    }

    @GetMapping("/savepage")
    public String mainpostpage(MainPostSaveDto mainPostSaveDto){
        return "MainPost_save";
    } //발리데이션 때문에.


    @PostMapping("/save")
    public String mainpostsave(@Valid MainPostSaveDto mainPostSaveDto, BindingResult bindingResult){ //BindingResult 매개변수는 @Valid 애너테이션으로 인해 검증이 수행된 결과를 의미하는 객체이다.
        if(bindingResult.hasErrors()){
            return  "MainPost_save";
        }
        long savenum = mainPostsService.save(mainPostSaveDto.getUsername(), mainPostSaveDto.getTitle(), mainPostSaveDto.getContent());
        log.info("저장된 글 번호 : {}", savenum);
        return "redirect:/mainpost/mainlist";

    }

}
