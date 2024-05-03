package com.scme.messenger.dto.chat;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    @com.scme.messenger.validations.UUID
    private UUID chatId;

    @NotEmpty(message = "Your message should not be empty")
    private String content;

    private Date timestamp;

}
