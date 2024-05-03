package com.scme.messenger.model;

import com.scme.messenger.validations.UUID;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String filename;

    private String mime_type;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id")
    private ChatMessage chatMessage;
}
