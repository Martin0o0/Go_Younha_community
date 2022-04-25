package com.example.goyounhacom.web.Controller.MainPostsApiController;


import com.example.goyounhacom.Service.MainPostsService;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsGetDto;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mainpost/api")
public class MainPostApiController {
    private final MainPostsService mainPostsService;

    @PostMapping("/save")
    public String save(@RequestBody MainPostSaveDto mainPostSaveDto){
        log.info("글 제목 : {}", mainPostSaveDto.getTitle());
        log.info("글 내용 : {}", mainPostSaveDto.getContent());
        log.info("글 작성자 : {}", mainPostSaveDto.getUsername());
        return "등록된 글 번호 : " + mainPostsService.save(mainPostSaveDto);
    }

    @GetMapping("/get")
    public List<MainPostGetDto> findbyAll(){
        return mainPostsService.findByAll();
    }
}
