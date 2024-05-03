package com.scme.messenger.controller;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.ResponseDto;
import com.scme.messenger.dto.chat.ChatDto;
import com.scme.messenger.dto.chat.ChatPreviewResponseDto;
import com.scme.messenger.dto.chat.ChatResponseDto;
import com.scme.messenger.services.IChatService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/chats", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class ChatController {

    private final IChatService iChatService;


    @PostMapping
    @PreAuthorize("#chatDto.senderId == authentication.principal.username")
    public ResponseEntity<?> create(@Valid @RequestBody ChatDto chatDto){

        iChatService.create(chatDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_201)
                        .statusMsg(ResponseConstants.MESSAGE_201)
                        .build());
    }

    @DeleteMapping
    @PreAuthorize("#chatDto.senderId == authentication.principal.username")
    public ResponseEntity<?> delete(@Valid @RequestBody ChatDto chatDto){

        iChatService.delete(chatDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_200)
                        .statusMsg(ResponseConstants.MESSAGE_200)
                        .build());
    }

    @GetMapping("/{senderId}/{recepientId}")
    @PreAuthorize("#senderId == authentication.principal.username")
    public ResponseEntity<?> getChat(
            @PathVariable @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits") String senderId,
            @PathVariable @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits") String recepientId,
            @RequestParam(name = "page" , defaultValue = "0") int page,
            @RequestParam(name = "size" , defaultValue = "20") int size
    ){
        ChatResponseDto chatDto = iChatService.getChat(senderId,recepientId, page ,size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(chatDto);
    }

    @GetMapping("/{senderId}")
    @PreAuthorize("#senderId == authentication.principal.username")
    public ResponseEntity<?> getAllChats(
            @PathVariable @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits") String senderId
    ){
        List<ChatPreviewResponseDto> messages = iChatService.getAllChats(senderId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(messages);
    }

}
