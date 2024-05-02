package com.scme.messenger.dto.group;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupNotificationDto {
    private String groupId;
    private String senderId;
    private String content;
}
