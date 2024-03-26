package com.scme.messenger.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.scme.messenger.dto.ErrorReponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorReponseDTO> handleBadRequestException(BadRequestException exception,
            WebRequest webRequest) {

        ErrorReponseDTO errorReponseDTO = ErrorReponseDTO.builder()
                .apiPath(webRequest.getDescription(false))
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorReponseDTO, HttpStatus.BAD_REQUEST);
    }

}
