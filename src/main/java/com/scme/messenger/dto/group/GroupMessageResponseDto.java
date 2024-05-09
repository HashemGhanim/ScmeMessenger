package com.scme.messenger.dto.group;

import com.scme.messenger.dto.userdto.UserResponseDto;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessageResponseDto {
    private UUID messageId;
    private UserResponseDto sender;
    private String content;
    private String filename;
    private String mime_type;
    private String data;
    private Date timestamp;
}
