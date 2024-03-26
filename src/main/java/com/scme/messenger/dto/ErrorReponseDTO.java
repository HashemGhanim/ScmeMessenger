package com.scme.messenger.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ErrorReponseDTO {

    private String apiPath;

    private HttpStatus errorCode;

    private String errorMessage;

    private LocalDateTime errorTime;
}
