package com.scme.messenger.dto.authenticationdto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class AuthenticationRequestDTO {
    @NotNull(message = "User ID can not be a null or empty")
    private String userId;
    @NotEmpty(message = "Password can not be a null or empty")
    private String password;
}
