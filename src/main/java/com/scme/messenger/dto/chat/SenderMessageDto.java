package com.scme.messenger.dto.chat;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SenderMessageDto {
    private UUID messageId;

    private String content;

    private String filename;

    private String mime_type;

    private String data;

    private Date timestamp;
}
