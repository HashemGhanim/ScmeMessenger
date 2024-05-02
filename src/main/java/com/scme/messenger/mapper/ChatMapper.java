package com.scme.messenger.mapper;

import com.scme.messenger.dto.chat.ChatDto;
import com.scme.messenger.model.Chat;
import com.scme.messenger.model.User;
import com.scme.messenger.model.composite.ChatID;
import com.scme.messenger.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    private final UserRepo userRepo;
    public Chat convertToChat(ChatDto chatDto){

        User sender = userRepo.getReferenceById(chatDto.getSenderId());
        User recepient = userRepo.getReferenceById(chatDto.getRecepientId());

        return Chat.builder()
                .chatID(ChatID.builder()
                        .senderId(chatDto.getSenderId())
                        .recepientId(chatDto.getRecepientId())
                        .build())
                .sender(sender)
                .recepient(recepient)
                .build();
    }

    public ChatDto convertToChatDto(Chat chat){
        return ChatDto.builder()
                .recepientId(chat.getChatID().getRecepientId())
                .senderId(chat.getChatID().getSenderId())
                .build();
    }
}
