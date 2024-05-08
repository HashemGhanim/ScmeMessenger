package com.scme.messenger.dto.group;

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
    private String courseId;
    private String moduleId;
    private String senderId;
    private String content;
    private String filename;
    private String mime_type;
    private String data;
    private Date timestamp;
}
