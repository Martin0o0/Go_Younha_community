package com.example.goyounhacom.web.Controller.Notice;

import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.Service.NoticeService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.Infomation.Notice;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.web.Dto.Imformation.NoticeSaveDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

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
        User user = userService.getbyUsername(principal.getName());
        if (bindingResult.hasErrors()) {
            return "Notice_save";
        }
        long savenum = noticeService.save(noticeSaveDto, user);
        return "redirect:/admin/information";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/admin/notice/{id}")//어드민만 들어갈 수 있는 페이지.
    public String adminNoticeInfo(@PathVariable Long id, Model model){
        Notice notice = noticeService.getById(id);
        model.addAttribute("notice", notice);
        return "Admin/NoticeInfo";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/admin/notice/put/{id}")
    public String adminNotice(@PathVariable Long id, Principal principal, Model model, NoticeSaveDto noticeSaveDto){
        model.addAttribute("posttype", "공지사항 수정");
        Notice notice = noticeService.getById(id);
        if(notice.getUser().getUsername().equals(principal.getName()) == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한 없음");
        }
        noticeSaveDto.setTitle(notice.getTitle());
        noticeSaveDto.setContent(notice.getContent());
        return "Notice_save";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/admin/notice/put/{id}")
    public String adminNotice(@PathVariable Long id, @Valid NoticeSaveDto noticeSaveDto, BindingResult bindingResult, Principal principal, Model model){
        Notice notice = noticeService.getById(id);
        model.addAttribute("posttype", "공지사항 수정");
        if(bindingResult.hasErrors()){
            return "Notice_save";
        }
        if(notice.getUser().getUsername().equals(principal.getName()) == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한 없음");
        }
        User user = userService.getbyUsername(principal.getName());
        noticeSaveDto.setUser(user);
        noticeService.update(id, noticeSaveDto);
        return "redirect:/admin/notice/"+id;
    }

}
