package com.scme.messenger.model.composite;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Embeddable
public class ChatMessageID implements Serializable {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id")
    private UUID messageId;

    @Column(name = "chat_id")
    private UUID chatId;

    @Column(name = "sender_id")
    private Integer senderId;

    @Column(name = "recepient_id")
    private Integer recepientId;
}
