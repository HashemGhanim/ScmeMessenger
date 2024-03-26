package com.scme.messenger.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.AuthenticationRequestDTO;
import com.scme.messenger.dto.AuthenticationResponseDTO;
import com.scme.messenger.model.User;
import com.scme.messenger.repository.UserRepo;
import com.scme.messenger.services.IAuthService;
import com.scme.messenger.services.JwtService;

import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IAuthServiceImpl implements IAuthService {

    @NonNull
    private final AuthenticationManager authenticationManager;

    @NonNull
    private final UserRepo userRepo;

    @NonNull
    private final JwtService jwtService;

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserId(),
                        request.getPassword()));

        User user = userRepo.getReferenceById(request.getUserId());

        String token = jwtService.generateToken(user);

        AuthenticationResponseDTO response = AuthenticationResponseDTO.builder()
                .statusCode(ResponseConstants.STATUS_200)
                .statusMsg(ResponseConstants.MESSAGE_200)
                .token(token)
                .build();

        return response;
    }

}
