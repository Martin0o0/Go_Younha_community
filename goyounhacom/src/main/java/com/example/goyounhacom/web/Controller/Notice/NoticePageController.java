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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/noticelist")
    public String UserNotice(Model model, @PageableDefault(sort="id", direction = Sort.Direction.DESC, size = 5) Pageable pageable, @AuthenticationPrincipal PrincipalDatails principalDatails){
        Page<Notice> list = noticeService.pagelist(pageable);
        model.addAttribute("notice", list);
        if(principalDatails != null){
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }

        return "UserNotice";
    }



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

    @GetMapping("/notice/{id}")//공지사항 입장.
    public String adminNoticeInfo(@PathVariable Long id, Model model, @AuthenticationPrincipal PrincipalDatails principalDatails){
        Notice notice = noticeService.getById(id);
        model.addAttribute("notice", notice);
        if(principalDatails != null){
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }
        return "Admin/NoticeInfo";
    }

    @GetMapping("/user/notice/{id}")//공지사항 입장.
    public String userNoticeInfo(@PathVariable Long id, Model model, @AuthenticationPrincipal PrincipalDatails principalDatails){
        Notice notice = noticeService.getById(id);
        model.addAttribute("notice", notice);
        if(principalDatails != null){
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }
        return "usernoticeinfo";
    }



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/admin/notice/put/{id}")
    public String adminNotice(@PathVariable Long id, Principal principal, Model model, NoticeSaveDto noticeSaveDto){
        model.addAttribute("posttype", "공지사항 수정");
        Notice notice = noticeService.getById(id);
        User user = userService.getbyUsername(principal.getName());
        if (user.getRoleKey().contentEquals("ROLE_ADMIN") || notice.getUser().getUsername().equals(principal.getName()) == true ) {
            noticeSaveDto.setTitle(notice.getTitle());
            noticeSaveDto.setContent(notice.getContent());
            return "Notice_save";
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/admin/notice/put/{id}")
    public String adminNotice(@PathVariable Long id, @Valid NoticeSaveDto noticeSaveDto, BindingResult bindingResult, Principal principal, Model model){
        Notice notice = noticeService.getById(id);
        model.addAttribute("posttype", "공지사항 수정");
        if(bindingResult.hasErrors()){
            return "Notice_save";
        }
        User user = userService.getbyUsername(principal.getName());

        if (user.getRoleKey().contentEquals("ROLE_ADMIN") || notice.getUser().getUsername().equals(principal.getName()) == true ) {
            noticeSaveDto.setUser(user);
            noticeService.update(id, noticeSaveDto);
            return "redirect:/notice/"+id;
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/admin/notice/delete/{id}")
    public String adminNoticeDelete(@PathVariable Long id, Principal principal){
        Notice notice = noticeService.getById(id);
        User user = userService.getbyUsername(principal.getName());
        if (user.getRoleKey().contentEquals("ROLE_ADMIN")) {
            long deletenum = noticeService.delete(id);
            log.info("삭제된 글 번호 : {}", deletenum);
            return "redirect:/admin/information";
        } else if (notice.getUser().getUsername().equals(principal.getName()) == true) {
            long deletenum = noticeService.delete(id);
            log.info("삭제된 글 번호 : {}", deletenum);
            return "redirect:/admin/information";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음");
        }
    }

}
