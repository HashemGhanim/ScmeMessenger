package com.scme.messenger.dto.authenticationdto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpCode {

    @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits")
    private String userId;

    @Pattern(regexp = "^\\d{4}$", message = "OTP must be 6 digits")
    private String otp;
}
