package com.scme.messenger.dto.chat;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotificationDto {
    private
    private String senderId;
    private String recepientId;
    private String content;
    private String filename;
    private String mime_type;
    private String data;
    private Date timestamp;
}
