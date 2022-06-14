package com.example.goyounhacom.chat;


import com.example.goyounhacom.Config.PrincipalDatails;
import com.example.goyounhacom.Service.ChatService;
import com.example.goyounhacom.Service.UserService;
import com.example.goyounhacom.domain.Users.User;
import com.example.goyounhacom.domain.chat.ChatRepository;
import com.example.goyounhacom.domain.chat.Message;
import com.example.goyounhacom.domain.chat.RoomIdRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
@Controller
public class RoomController {

    private final ChatRepository chatRepository;
    private final UserService userService;
    private final ChatService chatService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/rooms")
    public String rooms(@AuthenticationPrincipal PrincipalDatails principalDatails, Model model){
        log.info("모든 채팅방 리스트");
        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);
        }
        model.addAttribute("list", chatRepository.findAllRooms());

        return "rooms";
    }

    //채팅방 개설
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/room")
    public String create(@RequestParam String name, RedirectAttributes rttr){

        log.info("# Create Message Room , name: " + name);
        rttr.addFlashAttribute("roomName", chatRepository.createChatRoomDTO(name)); //뒤에 쿼리문을 생성하지 않음.

        return "redirect:/chat/rooms";
    }

    //채팅방 조회
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/room")
    public String getRoom(@RequestParam String roomId, Model model, @AuthenticationPrincipal PrincipalDatails principalDatails){

        if (principalDatails != null) {
            User user = userService.getbyUsername(principalDatails.getUsername());
            model.addAttribute("userinfo", user);

            model.addAttribute("room", chatService.findByroomname(roomId, user.getUsername()));
            //List<Message> list = chatService.findListMsg(roomId); //전체 챗팅 리스트 받아와서,
            List<LocalDate> datelist = chatService.finddistict(roomId); //
            Map<LocalDate, Object> map = new LinkedHashMap<>(); //순서가 지켜지는 맵 생성하고,
            for(LocalDate i : datelist){
                map.put(i, chatService.findByLocalDate(i));
            }
            log.info("{}", map.entrySet().toString());


            model.addAttribute("msglist", map);

        }
        log.info("# get Message Room, roomID : " + roomId);
        return "room";
    }
}
