package com.example.goyounhacom.domain.chat;

import com.example.goyounhacom.web.Dto.MessageDto.ChatRoomDto;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRepository {
    private Map<String, ChatRoomDto> chatRoomDtoMap;

    @PostConstruct
    private void init(){
        chatRoomDtoMap = new LinkedHashMap<>();

    }



    public List<ChatRoomDto> findAllRooms(){
        List<ChatRoomDto> result = new ArrayList<>(chatRoomDtoMap.values());
        Collections.reverse(result); //최신순으로 해야함으로, 역순으로 돌리자.

        return result;

    }

    public ChatRoomDto findRoomById(String id){
        return chatRoomDtoMap.get(id);
    }

    public ChatRoomDto createChatRoomDTO(String name){
        ChatRoomDto chatRoomDto = ChatRoomDto.create(name);
        chatRoomDtoMap.put(chatRoomDto.getRoomid(), chatRoomDto);

        return chatRoomDto;
    }
}
