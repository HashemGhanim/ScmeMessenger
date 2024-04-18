package com.scme.messenger.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scme.messenger.model.composite.ChatMessageID;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_message")
public class ChatMessage extends BaseEntity implements Serializable {

    @EmbeddedId
    private ChatMessageID chatMessageID;

    @JsonIgnore
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "chat_id", referencedColumnName = "chat_id", insertable = false, updatable = false),
            @JoinColumn(name = "sender_id", referencedColumnName = "sender_id", insertable = false, updatable = false),
            @JoinColumn(name = "recepient_id", referencedColumnName = "recepient_id", insertable = false, updatable = false)
    })
    private Chat chat;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User senderMessage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "recepient_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User recepientMessage;
}
