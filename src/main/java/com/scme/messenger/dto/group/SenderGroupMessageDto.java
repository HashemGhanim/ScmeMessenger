package com.scme.messenger.dto.group;

import com.scme.messenger.dto.userdto.SenderDto;
import com.scme.messenger.dto.userdto.UserDTO;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SenderGroupMessageDto {
    private UUID messageId;
    private String courseId;
    private String moduleId;
    private String iv;
    private SenderDto sender;
    private String content;
    private String filename;
    private String mime_type;
    private String data;
    private Date timestamp;
}
