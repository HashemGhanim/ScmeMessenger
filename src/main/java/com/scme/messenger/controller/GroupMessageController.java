package com.scme.messenger.controller;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.ResponseDto;
import com.scme.messenger.dto.group.*;
import com.scme.messenger.services.IChatMessageService;
import com.scme.messenger.services.IGroupMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
public class GroupMessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final IGroupMessageService iGroupMessageService;

    @MessageMapping("/group")
    public void processGroupMessage(@Payload GroupMessageDto groupMessageDto){
        SenderGroupMessageDto message = iGroupMessageService.save(groupMessageDto);
//        log.info(groupMessageDto.getModuleId() + " " + groupMessageDto.getCourseId());
        simpMessagingTemplate.convertAndSend("/group/"+groupMessageDto.getModuleId()+"/"+groupMessageDto.getCourseId(),
                message
        );
    }

    @DeleteMapping("/group/message")
    @PreAuthorize("hasRole('ROLE_DOCTOR') || #messageIdDto.senderId == authentication.principal.username")
    public ResponseEntity<?> deleteGroupMessage(@Valid @RequestBody GroupMessageIdDto messageIdDto){
        iGroupMessageService.delete(messageIdDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .statusCode(ResponseConstants.STATUS_200)
                                .statusMsg(ResponseConstants.MESSAGE_200)
                                .build()
                );
    }

    @PatchMapping("/group/message")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> pinGroupMessage(@Valid @RequestBody GroupMessageIdPinDto groupMessageIdPinDto){
        iGroupMessageService.pinMessage(groupMessageIdPinDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_201)
                        .statusMsg(ResponseConstants.MESSAGE_201)
                        .build());
    }
}
