package com.scme.messenger.model;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scme.messenger.model.composite.ChatID;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat")
public class Chat extends BaseEntity implements Serializable {

    @EmbeddedId
    private ChatID chatID;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User sender;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "recepient_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User recepient;

    @OneToMany(mappedBy = "firstChat", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Set<ChatMessage> senderChatMessages;

    @OneToMany(mappedBy = "secondChat", fetch = FetchType.LAZY)
    private Set<ChatMessage> recepientChatMessages;

}
