package com.scme.messenger.model;

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
public class GroupMessageAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String filename;

    private String mime_type;

    @Column(columnDefinition = "TEXT")
    private String data;

    @OneToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GroupMessage groupMessage;
}
