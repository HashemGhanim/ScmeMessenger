package com.scme.messenger.controller;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.ResponseDto;
import com.scme.messenger.dto.subscription.SubscriptionDto;
import com.scme.messenger.services.ISubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/subscribe/course", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class SubscriptionController {

    private final ISubscriptionService iSubscriptionService;

    @PostMapping
    public ResponseEntity<?> subscribeUser(@Valid @RequestBody SubscriptionDto subscriptionDto){

        iSubscriptionService.subscribe(subscriptionDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseDto.builder()
                                .statusCode(ResponseConstants.STATUS_201)
                                .statusMsg(ResponseConstants.MESSAGE_201)
                                .build()
                );
    }

    @DeleteMapping
    public ResponseEntity<?> unSubscribeUser(@Valid @RequestBody SubscriptionDto subscriptionDto){

        iSubscriptionService.unSubscribe(subscriptionDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .statusCode(ResponseConstants.STATUS_200)
                                .statusMsg(ResponseConstants.MESSAGE_200)
                                .build()
                );
    }
}
