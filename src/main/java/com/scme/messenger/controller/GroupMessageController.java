package com.scme.messenger.controller;

import com.scme.messenger.dto.group.GroupMessageDto;
import com.scme.messenger.dto.group.GroupNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
public class GroupMessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/group")
    public void processGroupMessage(@Payload GroupMessageDto groupMessageDto){
        simpMessagingTemplate.convertAndSend("/group/"+groupMessageDto.getCourseId(),
                GroupNotificationDto.builder()
                        .groupId(groupMessageDto.getCourseId())
                        .content(groupMessageDto.getContent())
                        .senderId(groupMessageDto.getSenderId())
                        .build()
        );
    }
}
