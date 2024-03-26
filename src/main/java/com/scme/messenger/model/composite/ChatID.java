package com.scme.messenger.model.composite;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ChatID implements Serializable {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "chat_id")
    private UUID charId;

    @Column(name = "sender_id")
    private Integer senderId;

    @Column(name = "recepient_id")
    private Integer recepientId;
}
