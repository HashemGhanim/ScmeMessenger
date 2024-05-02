package com.scme.messenger.controller;

import java.time.LocalDateTime;

import com.scme.messenger.dto.authenticationdto.AuthenticationResponseDTO;
import com.scme.messenger.dto.authenticationdto.OtpCode;
import com.scme.messenger.dto.authenticationdto.PasswordDto;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.ResponseDto;
import com.scme.messenger.dto.authenticationdto.AuthenticationRequestDTO;
import com.scme.messenger.services.IAuthService;
import com.scme.messenger.services.IEmailJobService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final IAuthService authService;

    private final IEmailJobService emailJobService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequestDTO request) throws SchedulerException {

        AuthenticationResponseDTO res = authService.authenticate(request);

        if (res == null)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDto.builder()
                            .statusCode(ResponseConstants.STATUS_200)
                            .statusMsg(ResponseConstants.MESSAGE_200)
                            .build());

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/verify/{userId}")
    public ResponseEntity<?> verifyUserWithOtp(
            @PathVariable @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits") String userId)
            throws SchedulerException {

        emailJobService.scheduleJobs(LocalDateTime.now(), userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(ResponseConstants.STATUS_201, ResponseConstants.MESSAGE_201));
    }

    @PostMapping("/otp")
    public ResponseEntity<?> verfiyOtp(@Valid @RequestBody OtpCode otpCode) {

        AuthenticationResponseDTO res = authService.verifyOtp(otpCode);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PostMapping("/set-password")
    public ResponseEntity<?> setPassword(@Valid @RequestBody PasswordDto passwordDto) {
        authService.setPassword(passwordDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_201)
                        .statusMsg(ResponseConstants.MESSAGE_201)
                        .build());
    }

}
