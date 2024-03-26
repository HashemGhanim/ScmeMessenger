package com.scme.messenger.services;

import com.scme.messenger.dto.AuthenticationRequestDTO;
import com.scme.messenger.dto.AuthenticationResponseDTO;

public interface IAuthService {
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
}
