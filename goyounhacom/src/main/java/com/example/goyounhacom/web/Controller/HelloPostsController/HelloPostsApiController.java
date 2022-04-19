package com.example.goyounhacom.web.Controller.HelloPostsController;


import com.example.goyounhacom.Service.HelloPostsService;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class HelloPostsApiController {

    private final HelloPostsService helloPostsService;

    @PostMapping("/hellopost/save")
    public String save(@RequestBody HelloPostsSaveDto helloPostsSaveDto){
        log.info("글 제목 : {}", helloPostsSaveDto.getTitle());
        log.info("글 내용 : {}", helloPostsSaveDto.getContent());
        log.info("글 작성자 : {}", helloPostsSaveDto.getUserId());
        return "등록된 글 번호" + helloPostsService.save(helloPostsSaveDto);
    }
}
