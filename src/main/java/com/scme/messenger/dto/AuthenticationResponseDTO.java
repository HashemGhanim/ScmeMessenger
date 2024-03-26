package com.scme.messenger.dto;

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
public class AuthenticationResponseDTO {
    private String statusCode;
    private String statusMsg;
    private String token;
}
