package com.example.goyounhacom.web.Dto.MessageDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessageDto {
    private String roomId;
    private String writter;
    private String message;
}
