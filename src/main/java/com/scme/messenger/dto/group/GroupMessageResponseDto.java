package com.scme.messenger.dto.group;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessageResponseDto {
    private String senderId;
    private String content;
    private String filename;
    private String mime_type;
    private String data;
    private Date timestamp;

}
