package com.scme.messenger.services;

import com.scme.messenger.dto.chat.*;


public interface IChatMessageService {
    void save(ChatMessageDto chatMessageDto, String senderId , String recepientId);
    void deleteMessage(ChatMessageIdDto chatMessageIdDto);
    void editMessage(ChatMessageEditDto chatMessageEditDto);
    void messagesMarkSeen(MarkSeenRequest markSeenRequest);
}
