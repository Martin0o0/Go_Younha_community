package com.example.goyounhacom.web.Controller.Notice;

import com.example.goyounhacom.Service.NoticeService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.web.Dto.Imformation.NoticeSaveDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NoticePageController {
    private final UserService userService;
    private final NoticeService noticeService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/notice/save")
    public String NoticeSave(NoticeSaveDto noticeSaveDto, Model model){
        model.addAttribute("posttype", "공지사항 등록");
        return "Notice_save";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/notice/save")
    public String Noticesave(@Valid NoticeSaveDto noticeSaveDto, BindingResult bindingResult, Principal principal, Model model){
        model.addAttribute("postype", "공지사항 등록");
        if (bindingResult.hasErrors()) {
            return "MainPost_save";
        }
        long savenum = noticeService.save(noticeSaveDto);
        return "redirect:/admin/information";
    }

    //@GetMapping("/notice/{id}")

}
