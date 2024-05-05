package com.scme.messenger.controller;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.ResponseDto;
import com.scme.messenger.dto.chat.*;
import com.scme.messenger.services.IChatMessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
public class ChatMessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final IChatMessageService iChatMessageService;

    @MessageMapping("/chat/{senderId}/{recepientId}")
    public void processMessage(@Valid @Payload ChatMessageDto chatMessageDto ,
                               @DestinationVariable @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits") String senderId,
                               @DestinationVariable @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits") String recepientId){

        SenderMessageDto message = iChatMessageService.save(chatMessageDto , senderId , recepientId);
        simpMessagingTemplate.convertAndSendToUser(recepientId , "/queue/messages",
                ChatNotificationDto.builder()
                        .senderId(senderId)
                        .recepientId(recepientId)
                        .content(chatMessageDto.getContent())
                        .mime_type(chatMessageDto.getMime_type())
                        .filename(chatMessageDto.getFilename())
                        .data(chatMessageDto.getData())
                        .build()
        );
        simpMessagingTemplate.convertAndSendToUser(senderId , "/recent/send/message", message);
    }

    @DeleteMapping("/messages")
    @PreAuthorize("#chatMessageIdDto.senderId == authentication.principal.username")
    public ResponseEntity<?> deleteMessage(@Valid @RequestBody ChatMessageIdDto chatMessageIdDto){

        iChatMessageService.deleteMessage(chatMessageIdDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_200)
                        .statusMsg(ResponseConstants.MESSAGE_200)
                        .build());
    }

    @PatchMapping("/messages")
    @PreAuthorize("#chatMessageEditDto.senderId == authentication.principal.username")
    public ResponseEntity<?> editMessage(@Valid @RequestBody ChatMessageEditDto chatMessageEditDto){

        iChatMessageService.editMessage(chatMessageEditDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_200)
                        .statusMsg(ResponseConstants.MESSAGE_200)
                        .build());
    }

    @PatchMapping("/messages/mark-seen")
    @PreAuthorize("#markSeenRequest.senderId == authentication.principal.username")
    public ResponseEntity<?> messagesMarkSeen(@Valid @RequestBody MarkSeenRequest markSeenRequest){

        iChatMessageService.messagesMarkSeen(markSeenRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_200)
                        .statusMsg(ResponseConstants.MESSAGE_200)
                        .build());
    }
}
