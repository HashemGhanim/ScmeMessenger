package com.scme.messenger.dto.authenticationdto;

import com.scme.messenger.constants.ResponseConstants;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {

    @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits")
    private String userId;

    @NotEmpty(message = "Password can not be a null or empty")
    @Pattern(regexp = ResponseConstants.REG_EXP_FOR_PASSWORD, message = ResponseConstants.MSG_PASSWORD_NOT_VALID)
    private String password;

    @NotEmpty(message = "Password can not be a null or empty")
    @Pattern(regexp = ResponseConstants.REG_EXP_FOR_PASSWORD, message = ResponseConstants.MSG_PASSWORD_NOT_VALID)
    private String repeatedPassword;
}
