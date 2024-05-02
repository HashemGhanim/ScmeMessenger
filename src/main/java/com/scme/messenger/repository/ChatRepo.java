package com.scme.messenger.repository;

import com.scme.messenger.model.Chat;
import com.scme.messenger.model.composite.ChatID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRepo extends JpaRepository<Chat , ChatID> {
    @Query("SELECT case WHEN count(c) > 0 then true else false end from Chat c where c.chatID.senderId=?1 and c.chatID.recepientId=?2")
    boolean isChatExist(String senderId , String recId);

    @Query("SELECT c from Chat c where c.chatID.senderId=?1 and c.chatID.recepientId=?2")
    Chat findChatBySenderAndRecepient(String senderId , String recepientId);
}
