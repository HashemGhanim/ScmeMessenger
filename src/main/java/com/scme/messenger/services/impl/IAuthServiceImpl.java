package com.scme.messenger.services.impl;

import com.scme.messenger.dto.authenticationdto.OtpCode;
import com.scme.messenger.dto.authenticationdto.PasswordDto;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.mapper.UserOtpMapper;
import com.scme.messenger.model.UserOtp;
import com.scme.messenger.repository.UserOtpRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.authenticationdto.AuthenticationRequestDTO;
import com.scme.messenger.dto.authenticationdto.AuthenticationResponseDTO;
import com.scme.messenger.dto.userdto.UserDTO;
import com.scme.messenger.mapper.UserMapper;
import com.scme.messenger.model.User;
import com.scme.messenger.repository.UserRepo;
import com.scme.messenger.services.IAuthService;
import com.scme.messenger.services.JwtService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IAuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepo userRepo;

    private final JwtService jwtService;

    private final UserOtpRepo userOtpRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserId(),
                        request.getPassword()));

        return generateToken(request.getUserId());
    }

    @Override
    public AuthenticationResponseDTO verifyOtp(OtpCode otpCode) {

        UserOtp userOtp = Optional.of(userOtpRepo.getReferenceById(otpCode.getUserId()))
                .orElseThrow(() -> new BadRequestException(ResponseConstants.OTP_NOT_VALID));

        OtpCode userOtpCode = UserOtpMapper.convertToOtp(userOtp);

        if (!userOtpCode.getOtp().equals(otpCode.getOtp()))
            throw new BadRequestException(ResponseConstants.OTP_NOT_VALID);

        return generateToken(otpCode.getUserId());
    }

    @Override
    public void setPassword(PasswordDto password) {
        if (!password.getPassword().equals(password.getRepeatedPassword()))
            throw new BadRequestException(ResponseConstants.PASSWORD_NOT_VALID);

        final String userId = password.getUserId();
        User user = Optional.of(userRepo.getReferenceById(userId))
                .orElseThrow(() -> new BadRequestException(ResponseConstants.USER_NOT_FOUND));

        user.setPassword(passwordEncoder.encode(password.getPassword()));

        userRepo.save(user);
    }

    private AuthenticationResponseDTO generateToken(String userId) {
        User user = userRepo.getReferenceById(userId);

        UserDTO userDTO = UserMapper.convertUserToDTO(user, new UserDTO());

        String token = jwtService.generateToken(user);

        AuthenticationResponseDTO response = AuthenticationResponseDTO.builder()
                .statusCode(ResponseConstants.STATUS_200)
                .statusMsg(ResponseConstants.MESSAGE_200)
                .token(token)
                .user(userDTO)
                .build();

        return response;
    }
}
