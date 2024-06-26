package com.scme.messenger.controller;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.ResponseDto;
import com.scme.messenger.dto.group.*;
import com.scme.messenger.services.IGroupMessageService;
import com.scme.messenger.validations.AuthorizeSentGroupMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class GroupMessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final IGroupMessageService iGroupMessageService;

    @MessageMapping("/group")
    @AuthorizeSentGroupMessage
    public void processGroupMessage(@Payload GroupMessageDto groupMessageDto){
        SenderGroupMessageDto message = iGroupMessageService.save(groupMessageDto);
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

    @PatchMapping("/group/pin/message")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> pinGroupMessage(@Valid @RequestBody GroupMessageIdPinDto groupMessageIdPinDto){
        iGroupMessageService.pinMessage(groupMessageIdPinDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_201)
                        .statusMsg(ResponseConstants.MESSAGE_201)
                        .build());
    }

    @GetMapping("/group/pin/message/{userId}")
    @PreAuthorize("#userId == authentication.principal.username")
    public ResponseEntity<?> getPinnedGroupMessage(
            @PathVariable @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits") String userId
    ){
        List<GroupMessagePinResponseDto> messages = iGroupMessageService.getPinnedMessages(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(messages);
    }


}
