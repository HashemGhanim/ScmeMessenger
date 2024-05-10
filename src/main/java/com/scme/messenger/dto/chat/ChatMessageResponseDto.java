package com.scme.messenger.dto.chat;


import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponseDto {

    private UUID messageId;
    private UUID chatId;
    private String senderId;
    private String recepientId;
    private String iv;
    private String content;
    private String filename;
    private String mime_type;
    private String data;
    private Date timestamp;
    private boolean seen;
}
