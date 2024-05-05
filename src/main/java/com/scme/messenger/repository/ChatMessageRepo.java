package com.scme.messenger.repository;

import com.scme.messenger.model.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMessageRepo extends JpaRepository<ChatMessage , UUID> {

    @Query("select c from ChatMessage c where c.firstSenderId=?1 and c.firstRecepientId=?2")
    Page<ChatMessage> findChatMessageBySenderIdAndRecipinetId(String senderId , String recipientId , Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM chat_message  WHERE first_chat_id IS NULL AND second_chat_id IS NULL", nativeQuery = true)
    void deleteUnFoundMessages();

    @Modifying
    @Query("UPDATE ChatMessage m SET m.seen = true WHERE m.secondChatId = ?1 and m.secondSenderId =?2")
    void markAsSeen(UUID chatId , String senderId);
}
