package com.scme.messenger.services.impl;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.chat.ChatDto;
import com.scme.messenger.dto.chat.ChatResponseDto;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.mapper.ChatMapper;
import com.scme.messenger.mapper.ChatMessageMapper;
import com.scme.messenger.model.Chat;
import com.scme.messenger.model.ChatMessage;
import com.scme.messenger.model.composite.ChatID;
import com.scme.messenger.repository.ChatMessageRepo;
import com.scme.messenger.repository.ChatRepo;
import com.scme.messenger.services.IChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.xml.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class IChatServiceImpl implements IChatService {

    private final ChatRepo chatRepo;
    private final ChatMessageMapper chatMessageMapper;
    private final ChatMessageRepo chatMessageRepo;
    private final ChatMapper chatMapper;

    @Value("${messages.limit}")
    private long limitOfMessages;

    @Override
    public void create(ChatDto chatDto) {

        if(chatRepo.isChatExist(chatDto.getSenderId() , chatDto.getRecepientId()))
            throw new BadRequestException(ResponseConstants.CHAT_IS_ALREADY_EXIST);

        Chat chatSender = chatMapper.convertToChat(chatDto);

        if(chatSender.getSender().getBlocks().contains(chatSender.getRecepient()))
            throw new BadRequestException(ResponseConstants.USER_IS_ALREADY_BLOCKED);

        chatRepo.save(chatSender);

        if(!chatRepo.isChatExist(chatDto.getRecepientId(), chatDto.getSenderId())){
            Chat chatReception = Chat.builder()
                    .chatID(ChatID.builder()
                            .chatId(chatSender.getChatID().getChatId())
                            .senderId(chatSender.getChatID().getRecepientId())
                            .recepientId(chatSender.getChatID().getSenderId())
                            .build())
                    .build();
            chatRepo.save(chatReception);
        }
    }

    @Override
    public void delete(ChatDto chatDto) {
        if(!chatRepo.isChatExist(chatDto.getSenderId() , chatDto.getRecepientId()))
            throw new BadRequestException(ResponseConstants.CHAT_NOT_FOUND);

        Chat chat = chatRepo.findChatBySenderAndRecepient(chatDto.getSenderId(), chatDto.getRecepientId());

        chatRepo.delete(chat);
        chatMessageRepo.deleteUnFoundMessages();
    }

    @Override
    public ChatResponseDto getChat(String senderId, String recepientId , int page , int size) {

        Chat chat = Optional.of(chatRepo.findChatBySenderAndRecepient(senderId,recepientId)).orElseThrow(()-> new BadRequestException(ResponseConstants.CHAT_NOT_FOUND));

        Set<ChatMessage> senderMessages = chat.getSenderChatMessages();
        Set<ChatMessage> recepientMessages = chat.getRecepientChatMessages();

        List<ChatMessage> messages = recepientMessages.stream()
                .sorted(Comparator.comparing(ChatMessage::getTimestamp).reversed())
                .takeWhile(chatMessage -> !chatMessage.isSeen())
                .collect(Collectors.toList());

        long maxSize = (long) Math.ceil(Math.max(size , messages.size()) * 1.0 / limitOfMessages * 1.0);

        return ChatResponseDto.builder()
                .chatId(chat.getChatID().getChatId())
                .senderId(chat.getChatID().getSenderId())
                .recepientId(chat.getChatID().getRecepientId())
                .messages(Stream.concat(senderMessages.stream() , recepientMessages.stream())
                        .sorted(Comparator.comparing(ChatMessage::getTimestamp).reversed())
                        .skip(page * limitOfMessages)
                        .limit(maxSize * limitOfMessages)
                        .map(chatMessage -> chatMessageMapper.convertToChatMessageResponseDto(chatMessage))
                        .collect(Collectors.toList()))
                .block(chat.getSender().getBlocks().contains(chat.getRecepient()))
                .blocked(chat.getSender().getBlocked().contains(chat.getRecepient()))
                .build();
    }

}
