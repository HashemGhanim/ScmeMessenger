package com.scme.messenger.dto.chat;

import com.scme.messenger.dto.userdto.UserDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatPreviewResponseDto {
    private UUID chatId;
    private UserDTO recepient;
    private String secretKey;
    private ChatMessageResponseDto lastMessage;
    private int numberOfUnseenMessages;
    private boolean blocked;
    private boolean block;
}
