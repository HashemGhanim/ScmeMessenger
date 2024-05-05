package com.scme.messenger.mapper;

import com.scme.messenger.dto.chat.ChatMessageDto;
import com.scme.messenger.dto.chat.ChatMessageResponseDto;
import com.scme.messenger.model.Attachment;
import com.scme.messenger.model.Chat;
import com.scme.messenger.model.ChatMessage;
import com.scme.messenger.model.composite.ChatID;
import com.scme.messenger.repository.ChatRepo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Slf4j
@Component
public class ChatMessageMapper {

    @Autowired
    private ChatRepo chatRepo;


    public  ChatMessage convertToChatMessage(ChatMessageDto chatMessageDto , String senderId , String recepientId){
        ChatMessage c = new ChatMessage();

        Chat senderChat = chatRepo.getReferenceById(ChatID.builder()
                .chatId(chatMessageDto.getChatId())
                .recepientId(recepientId)
                .senderId(senderId)
                .build());

        Attachment attachment = Attachment.builder()
                .filename(chatMessageDto.getFilename())
                .mime_type(chatMessageDto.getMime_type())
                .data(chatMessageDto.getData())
                .build();

        Chat recipientChat = chatRepo.findChatBySenderAndRecepient(recepientId, senderId);

        c.setFirstChat(senderChat);
        c.setSecondChat(recipientChat);

        c.setFirstChatId(senderChat.getChatID().getChatId());
        c.setFirstSenderId(senderChat.getChatID().getSenderId());
        c.setFirstRecepientId(senderChat.getChatID().getRecepientId());

        c.setSecondChatId(recipientChat.getChatID().getChatId());
        c.setSecondSenderId(recipientChat.getChatID().getSenderId());
        c.setSecondRecepientId(recipientChat.getChatID().getRecepientId());

        c.setContent(chatMessageDto.getContent());
        c.setTimestamp(chatMessageDto.getTimestamp());

        c.setAttachment(attachment);

        return c;
    }

    public  ChatMessageDto convertToChatMessageDto(ChatMessage chatMessage){

        return ChatMessageDto.builder()
                .chatId(chatMessage.getFirstChatId())
                .content(chatMessage.getContent())
                .timestamp(chatMessage.getTimestamp())
                .filename(chatMessage.getAttachment() == null ? "":chatMessage.getAttachment().getFilename())
                .mime_type(chatMessage.getAttachment() == null ? "":chatMessage.getAttachment().getMime_type())
                .data(chatMessage.getAttachment() == null ? "":chatMessage.getAttachment().getData())
                .build();
    }

    public ChatMessageResponseDto convertToChatMessageResponseDto(ChatMessage chatMessage){
        if(chatMessage.getFirstChat() == null)
            return ChatMessageResponseDto.builder()
                    .messageId(chatMessage.getMessageId())
                    .chatId(null)
                    .senderId(chatMessage.getSecondRecepientId())
                    .recepientId(chatMessage.getSecondSenderId())
                    .content(chatMessage.getContent())
                    .timestamp(chatMessage.getTimestamp())
                    .seen(chatMessage.isSeen())
                    .filename(chatMessage.getAttachment() == null ? "":chatMessage.getAttachment().getFilename())
                    .data(chatMessage.getAttachment() == null ? "":chatMessage.getAttachment().getData())
                    .mime_type(chatMessage.getAttachment() == null ? "":chatMessage.getAttachment().getMime_type())
                    .build();

        return ChatMessageResponseDto.builder()
                .messageId(chatMessage.getMessageId())
                .chatId(chatMessage.getFirstChat().getChatID().getChatId())
                .senderId(chatMessage.getFirstSenderId())
                .recepientId(chatMessage.getFirstRecepientId())
                .content(chatMessage.getContent())
                .timestamp(chatMessage.getTimestamp())
                .seen(chatMessage.isSeen())
                .filename(chatMessage.getAttachment() == null ? "": chatMessage.getAttachment().getFilename())
                .data(chatMessage.getAttachment() == null ? "": chatMessage.getAttachment().getData())
                .mime_type(chatMessage.getAttachment() == null ? "": chatMessage.getAttachment().getMime_type())
                .build();
    }

}
