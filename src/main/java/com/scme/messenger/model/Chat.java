package com.scme.messenger.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scme.messenger.model.composite.ChatID;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
