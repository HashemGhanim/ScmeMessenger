package com.scme.messenger.services.impl;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.chat.*;
import com.scme.messenger.dto.userdto.UserDTO;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.mapper.ChatMapper;
import com.scme.messenger.mapper.ChatMessageMapper;
import com.scme.messenger.mapper.UserMapper;
import com.scme.messenger.model.Chat;
import com.scme.messenger.model.ChatMessage;
import com.scme.messenger.model.composite.ChatID;
import com.scme.messenger.repository.ChatMessageRepo;
import com.scme.messenger.repository.ChatRepo;
import com.scme.messenger.services.IChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private int limitOfMessages;

    @Transactional
    @Override
    public void create(ChatDto chatDto) throws Exception {

        if(chatRepo.isChatExist(chatDto.getSenderId() , chatDto.getRecepientId()))
            throw new BadRequestException(ResponseConstants.CHAT_IS_ALREADY_EXIST);

        Chat chatSender = chatMapper.convertToChat(chatDto);

        if(chatSender.getSender().getBlocks().contains(chatSender.getRecepient()))
            throw new BadRequestException(ResponseConstants.USER_IS_ALREADY_BLOCKED);

        Chat chatReception;

        if(!chatRepo.isChatExist(chatDto.getRecepientId(), chatDto.getSenderId())){

            chatReception = Chat.builder()
                    .chatID(ChatID.builder()
                            .chatId(chatSender.getChatID().getChatId())
                            .senderId(chatSender.getChatID().getRecepientId())
                            .recepientId(chatSender.getChatID().getSenderId())
                            .build())
                    .secretKey(chatSender.getSecretKey())
                    .build();

            chatRepo.save(chatSender);
            chatRepo.save(chatReception);

        }else{
            chatReception = chatRepo.getReferenceById(ChatID.builder()
                    .chatId(chatSender.getChatID().getChatId())
                    .senderId(chatSender.getChatID().getRecepientId())
                    .recepientId(chatSender.getChatID().getSenderId())
                    .build());

            chatSender.setSecretKey(chatReception.getSecretKey());
            chatRepo.save(chatSender);
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
    public ChatResponseDto getChat(String senderId, String recepientId , int page, int size) {

        Chat chat = Optional.of(chatRepo.findChatBySenderAndRecepient(senderId,recepientId)).orElseThrow(()-> new BadRequestException(ResponseConstants.CHAT_NOT_FOUND));

        return chatResponseDto(chat , page , size);
    }

    @Override
    public List<ChatPreviewResponseDto> getAllChats(String senderId) {
        List<Chat> chats  = chatRepo.findAllBySenderId(senderId);

        List<ChatPreviewResponseDto> listOfMessages = chats.stream()
                .map(chat -> chatPreviewResponseDto(chat))
                .collect(Collectors.toList());

        return listOfMessages;
    }


    private ChatResponseDto chatResponseDto(Chat chat , int page , int size){
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
                .recepient(UserMapper.convertUserToDTO(chat.getRecepient(), new UserDTO()))
                .secretKey(chat.getSecretKey())
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

    private ChatPreviewResponseDto chatPreviewResponseDto(Chat chat){
        Set<ChatMessage> sMessages = chat.getSenderChatMessages();
        Set<ChatMessage> rMessages = chat.getRecepientChatMessages();

        List<ChatMessage> messages = rMessages.stream()
                .sorted(Comparator.comparing(ChatMessage::getTimestamp).reversed())
                .takeWhile(chatMessage -> !chatMessage.isSeen())
                .collect(Collectors.toList());


        List<ChatMessageResponseDto> lastMessage = Stream.concat(sMessages.stream() , rMessages.stream())
                .sorted(Comparator.comparing(ChatMessage::getTimestamp).reversed())
                .skip(0)
                .limit(1)
                .map(chatMessage -> chatMessageMapper.convertToChatMessageResponseDto(chatMessage))
                .collect(Collectors.toList());

        return ChatPreviewResponseDto.builder()
                .chatId(chat.getChatID().getChatId())
                .recepient(UserMapper.convertUserToDTO(chat.getRecepient() , new UserDTO()))
                .secretKey(chat.getSecretKey())
                .lastMessage(lastMessage.size() > 0 ? lastMessage.get(0) : null)
                .numberOfUnseenMessages(messages.size())
                .block(chat.getSender().getBlocks().contains(chat.getRecepient()))
                .blocked(chat.getSender().getBlocked().contains(chat.getRecepient()))
                .build();
    }
}
