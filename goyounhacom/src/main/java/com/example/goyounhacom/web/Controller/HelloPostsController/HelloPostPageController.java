package com.example.goyounhacom.web.Controller.HelloPostsController;


import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.Service.HelloPostsService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.HelloPosts.HelloPost;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsGetDto;
import com.example.goyounhacom.web.Dto.HelloPostsDto.HelloPostsSaveDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostGetDto;
import com.example.goyounhacom.web.Dto.MainPostDto.MainPostSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/hellopost")
public class HelloPostPageController {
    private final HelloPostsService helloPostsService;
    private final UserService userService;

    @GetMapping("/hellolist")
    public String helopost(@AuthenticationPrincipal PrincipalDatails principalDatails,  Model model, @PageableDefault(sort="id", direction = Sort.Direction.DESC, size = 5) Pageable pageable){
        Page<HelloPost> list = helloPostsService.pageList(pageable);
        model.addAttribute("hello_post", list);
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }
        return "HelloPost";
    }

    @GetMapping("/application/{id}")
    public String HelloPostApply(@PathVariable Long id, Model model, @AuthenticationPrincipal PrincipalDatails principalDatails){
        HelloPostsGetDto helloPostsGetDto = helloPostsService.findbyid(id);
        model.addAttribute("hello_post", helloPostsGetDto);
        if(principalDatails != null){
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);

        }
        return "HelloPost_apply";
    }


    @PreAuthorize("isAuthenticated()") //????????? ??????
    @GetMapping("/save")
    public String hellopostpage(HelloPostsSaveDto helloPostsSaveDto,Model model ,@AuthenticationPrincipal PrincipalDatails principalDatails){
        log.info("????????? ??? ?????? : {} ", helloPostsSaveDto.getContent());
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        };
        return "HelloPost_save";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/save")
    public String hellopostsave(@Valid HelloPostsSaveDto helloPostsSaveDto, BindingResult bindingResult, Principal principal, @AuthenticationPrincipal PrincipalDatails principalDatails, Model model){
        User user = userService.getbyUsername(principal.getName());
        log.info("????????? ??? ?????? : {} ", helloPostsSaveDto.getContent());
        if(bindingResult.hasErrors()){
            model.addAttribute("userinfo", user);
            return  "HelloPost_save";
        }
        long savenum = helloPostsService.save(helloPostsSaveDto.getContent(), user);
        log.info("????????? ??? ?????? : {}", savenum);
        return "redirect:/hellopost/hellolist";

    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/put/{id}")
    public String hellopostput(@PathVariable Long id, Principal principal, HelloPostsSaveDto helloPostsSaveDto, @AuthenticationPrincipal PrincipalDatails principalDatails, Model model){
        HelloPostsGetDto helloPostsGetDto= helloPostsService.getHelloPosts(id);
        if(helloPostsGetDto.getUser().getUsername().equals(principal.getName()) == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "?????? ????????? ??????");
        }
        helloPostsSaveDto.setContent(helloPostsGetDto.getContent());
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }
        return "HelloPost_save";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/put/{id}")
    public String hellopostupdate(@PathVariable Long id, @Valid HelloPostsSaveDto helloPostsSaveDto, BindingResult bindingResult, Principal principal, Model model, @AuthenticationPrincipal PrincipalDatails principalDatails) { //BindingResult ??????????????? @Valid ????????????????????? ?????? ????????? ????????? ????????? ???????????? ????????????.

        if (bindingResult.hasErrors()) {
            if (principalDatails != null) {
                User user = userService.getbyUsername(principalDatails.getUsername());
                model.addAttribute("userinfo", user);
            }
            return "HelloPost_save";
        }
        HelloPostsGetDto Dto = helloPostsService.getHelloPosts(id);
        if(Dto.getUser().getUsername().equals(principal.getName()) == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "?????? ????????? ??????");
        }
        long savenum = helloPostsService.modify(id, helloPostsSaveDto.getContent());
        log.info("????????? ??? ?????? : {}", savenum);
        return "redirect:/hellopost/application/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String mainpoostdelete(@PathVariable Long id, Principal principal){
        HelloPostsGetDto Dto = helloPostsService.getHelloPosts(id);
        if(Dto.getUser().getUsername().equals(principal.getName()) == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "?????? ????????? ??????");
        }
        long deletenum = helloPostsService.deletebyid(id);
        log.info("????????? ??? ?????? : {}", deletenum);
        return "redirect:/";
    }


    @GetMapping("/updateholics/{id}")
    public String updateholics(@PathVariable Long id){
        log.info("????????? ?????? ?????? ?????? : {}" ,userService.updateholics(id));
        return "redirect:/hellopost/hellolist";
    }

}
