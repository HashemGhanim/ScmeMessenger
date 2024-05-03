package com.scme.messenger.services;

import com.scme.messenger.dto.chat.ChatDto;
import com.scme.messenger.dto.chat.ChatResponseDto;

import java.util.List;

public interface IChatService {
    void create(ChatDto chatDto);
    void delete(ChatDto chatDto);
    ChatResponseDto getChat(String senderId, String recepientId , int page, int size);
    List<ChatResponseDto> getAllChats(String senderId);
}
