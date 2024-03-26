package com.scme.messenger.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scme.messenger.dto.AuthenticationRequestDTO;
import com.scme.messenger.services.impl.IAuthServiceImpl;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(path = "/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {

    @NonNull
    private final IAuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequestDTO request) {
        log.info("The request in the service");
        return ResponseEntity.ok().body(authService.authenticate(request));
    }
}
