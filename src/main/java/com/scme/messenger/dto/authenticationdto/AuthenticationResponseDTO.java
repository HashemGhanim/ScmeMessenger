package com.scme.messenger.dto.authenticationdto;

import com.scme.messenger.dto.userdto.UserDTO;

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
public class AuthenticationResponseDTO {
    private String statusCode;
    private String statusMsg;
    private String token;
    private UserDTO user;
}
