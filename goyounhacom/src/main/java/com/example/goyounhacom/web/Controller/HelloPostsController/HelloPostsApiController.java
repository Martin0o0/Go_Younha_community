package com.example.goyounhacom.web.Controller.HelloPostsController;


import com.example.goyounhacom.Service.HelloPostsService;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsGetDto;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class HelloPostsApiController {

    private final HelloPostsService helloPostsService;

    @PostMapping("/hellopost/post")
    public String save(@RequestBody HelloPostsSaveDto helloPostsSaveDto){
        log.info("글 제목 : {}", helloPostsSaveDto.getTitle());
        log.info("글 내용 : {}", helloPostsSaveDto.getContent());
        log.info("글 작성자 : {}", helloPostsSaveDto.getUserId());
        return "등록된 글 번호 : " + helloPostsService.save(helloPostsSaveDto);
    }

    @PutMapping("/hellopost/put/{id}")
    public String udpate(@PathVariable Long id, @RequestBody HelloPostsUpdateDto helloPostsUpdateDto){
        log.info("수정 글 제목 : {}", helloPostsUpdateDto.getTitle());
        log.info("수정 글 내용 : {}", helloPostsUpdateDto.getContent());
        log.info("수정 글 작성자 : {}", helloPostsUpdateDto.getUserId());
        return "수정된 글 번호 : " + helloPostsService.update(id, helloPostsUpdateDto);
    }

    @GetMapping("/hellopost/get")
    public List<HelloPostsGetDto> findbyAll(){
        return helloPostsService.findByAll();
    }


    @GetMapping("/hellopost/get/id/{id}")
    public HelloPostsGetDto getbyid(@PathVariable Long id) {
        return helloPostsService.findbyid(id);
    }


    @GetMapping("/hellopost/get/key/{key}")
    public List<HelloPostsGetDto> findBySearchwithkey(@PathVariable String key){
        return helloPostsService.findbysearchlist(key);
    }


    @DeleteMapping("/hellopost/delete/{id}")
    public String deletebyid(@PathVariable Long id){
        return "삭제된 글 번호 : " + helloPostsService.deletebyid(id);

    }

    @GetMapping("/hellopost/get/userid/{userid}")
    public List<HelloPostsGetDto> findbyuserid(@PathVariable String userid){
        return helloPostsService.findbyuserid(userid);
    }


}
