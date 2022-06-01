package com.example.goyounhacom.web.Controller.ChatController;

import com.example.goyounhacom.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatCreateController {
    private final ChatService chatService;


    @GetMapping("/chat/create/{username}")
    public String createChat(@PathVariable String username, @RequestParam String wantusername){
        log.info("path: {}", username);
        log.info("requestparam : {}", wantusername);
        String roomname = UUID.randomUUID().toString();
        chatService.makeRoom(username, wantusername, roomname);
        chatService.makeRoom(wantusername, username, roomname); //상대방 또한, 방을 판 사람을 알아야 함으로.
        return "redirect:/chat/room?roomId="+roomname;
    }






}
