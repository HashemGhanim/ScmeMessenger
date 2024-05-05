package com.scme.messenger.services.impl;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.chat.*;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.mapper.ChatMessageMapper;
import com.scme.messenger.model.Chat;
import com.scme.messenger.model.ChatMessage;
import com.scme.messenger.repository.ChatMessageRepo;
import com.scme.messenger.services.IChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class IChatMessageServiceImpl implements IChatMessageService {

    private final ChatMessageRepo chatMessageRepo;
    private final ChatMessageMapper chatMessageMapper;

    @Transactional
    @Override
    public SenderMessageDto save(ChatMessageDto chatMessageDto, String senderId , String recepientId) {

        ChatMessage chat = chatMessageMapper.convertToChatMessage(chatMessageDto, senderId , recepientId);

        if(checkIfIsBlockedUser(chat))
            throw new BadRequestException(ResponseConstants.USER_IS_ALREADY_BLOCKED);

        ChatMessage res = chatMessageRepo.save(chat);

        return SenderMessageDto.builder()
                .messageId(res.getMessageId())
                .chatId(res.getFirstChatId())
                .senderId(res.getFirstSenderId())
                .recepientId(res.getFirstRecepientId())
                .content(res.getContent())
                .data(res.getAttachment() == null ? "" : res.getAttachment().getData())
                .filename(res.getAttachment() == null ? "" : res.getAttachment().getFilename())
                .mime_type(res.getAttachment() == null ? "" : res.getAttachment().getMime_type())
                .timestamp(res.getTimestamp())
                .build();
    }


    @Override
    public void deleteMessage(ChatMessageIdDto chatMessageIdDto) {

        Optional<ChatMessage> message = findMessage(chatMessageIdDto.getMessageId());

        if(message.isPresent())
            chatMessageRepo.delete(message.get());
    }

    @Override
    public void editMessage(ChatMessageEditDto chatMessageEditDto) {

        Optional<ChatMessage> message = findMessage(chatMessageEditDto.getMessageId());

        if(message.isPresent() && !checkIfIsBlockedUser(message.get())){
            message.get().setContent(chatMessageEditDto.getContent());
            chatMessageRepo.save(message.get());
        }

    }

    @Transactional
    @Override
    public void messagesMarkSeen(MarkSeenRequest markSeenRequest) {
        chatMessageRepo.markAsSeen(markSeenRequest.getChatId() , markSeenRequest.getSenderId());
    }


    @Transactional
    public boolean checkIfIsBlockedUser(ChatMessage chatMessage) {
        Chat chat = chatMessage.getFirstChat();

        log.info(chat.getChatID().toString());

        if(chat != null && (chat.getSender().getBlocked().contains(chat.getRecepient())))
            return true;
        else if(chat != null && (chat.getSender().getBlocks().contains(chat.getRecepient())))
            return true;

        return false;
    }

    private Optional<ChatMessage> findMessage(UUID messageID){
        Optional<ChatMessage> message = Optional.of(chatMessageRepo.findById(messageID)).orElseThrow(()-> new BadRequestException(ResponseConstants.MESSAGE_NOT_FOUND));
        return message;
    }
}
