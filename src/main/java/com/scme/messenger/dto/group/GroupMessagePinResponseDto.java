package com.scme.messenger.dto.group;

import com.scme.messenger.dto.userdto.UserResponseDto;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessagePinResponseDto {
    private UUID messageId;
    private UserResponseDto sender;
    private GroupDto group;
    private String iv;
    private String content;
    private String filename;
    private String mime_type;
    private String data;
    private boolean isPinned;
    private Date timestamp;
}
