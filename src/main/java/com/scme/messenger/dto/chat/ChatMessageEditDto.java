package com.scme.messenger.dto.chat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageEditDto {

    @com.scme.messenger.validations.UUID
    private UUID messageId;

    @com.scme.messenger.validations.UUID
    private UUID chatId;

    @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits")
    private String senderId;

    @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits")
    private String recepientId;

    @NotEmpty(message = "Your message should not be empty")
    private String content;

    private String iv;
}
