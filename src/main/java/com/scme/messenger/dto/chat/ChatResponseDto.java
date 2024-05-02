package com.scme.messenger.dto.chat;

import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDto {
    private UUID chatId;
    private String senderId;
    private String recepientId;
    private List<ChatMessageResponseDto> messages;
    private boolean blocked;
    private boolean block;
}
