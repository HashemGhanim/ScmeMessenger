package com.scme.messenger.dto.chat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarkSeenRequest {

    @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits")
    private String senderId;
    @NotEmpty
    private List<UUID> messageIds;
}
