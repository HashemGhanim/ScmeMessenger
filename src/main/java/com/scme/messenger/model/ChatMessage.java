package com.scme.messenger.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_message")
public class ChatMessage extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id")
    private UUID messageId;

    @Column(name = "first_chat_id")
    private UUID firstChatId;

    @Column(name = "first_sender_id")
    private String firstSenderId;

    @Column(name = "first_recepient_id")
    private String firstRecepientId;

    @Column(name = "second_chat_id")
    private UUID secondChatId;

    @Column(name = "second_sender_id")
    private String secondSenderId;

    @Column(name = "second_recepient_id")
    private String secondRecepientId;

    private boolean seen = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "first_chat_id", referencedColumnName = "chat_id", insertable = false , updatable = false),
            @JoinColumn(name = "first_sender_id", referencedColumnName = "sender_id", insertable = false , updatable = false),
            @JoinColumn(name = "first_recepient_id", referencedColumnName = "recepient_id", insertable = false , updatable = false)
    })
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Chat firstChat;

    @JsonIgnore
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "second_chat_id", referencedColumnName = "chat_id", insertable = false , updatable = false),
            @JoinColumn(name = "second_sender_id", referencedColumnName = "sender_id" , insertable = false , updatable = false),
            @JoinColumn(name = "second_recepient_id", referencedColumnName = "recepient_id" , insertable = false , updatable = false)
    })
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Chat secondChat;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "first_sender_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User firstSenderUser;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "first_recepient_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User firstRecepientUser;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "second_sender_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User secondSenderUser;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "second_recepient_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User secondRecepientUser;


    private String content;
    private Date timestamp;

    @OneToOne(mappedBy = "chatMessage", fetch = FetchType.EAGER,cascade = CascadeType.ALL , orphanRemoval = true)
    private Attachment attachment;

    @PrePersist
    @PreUpdate
    private void validate(){
        if(content == null && attachment == null){
            throw new IllegalStateException("Either content or attachment must be provided.");
        }
    }
}
