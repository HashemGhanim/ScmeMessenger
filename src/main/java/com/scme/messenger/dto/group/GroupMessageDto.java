package com.scme.messenger.dto.group;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessageDto {
    private String courseId;
    private String moduleId;
    private String senderId;
    private String content;
    private Date timestamp;
}
