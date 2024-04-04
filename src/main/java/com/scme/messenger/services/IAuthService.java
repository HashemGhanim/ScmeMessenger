package com.scme.messenger.services;

import com.scme.messenger.dto.authenticationdto.AuthenticationRequestDTO;
import com.scme.messenger.dto.authenticationdto.AuthenticationResponseDTO;
import com.scme.messenger.dto.authenticationdto.OtpCode;
import com.scme.messenger.dto.authenticationdto.PasswordDto;

public interface IAuthService {

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);

    void verfiyOtp(OtpCode otpCode);

    void setPassword(PasswordDto password);
}
