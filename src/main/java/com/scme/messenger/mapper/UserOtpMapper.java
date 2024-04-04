package com.scme.messenger.mapper;

import com.scme.messenger.dto.authenticationdto.OtpCode;
import com.scme.messenger.model.UserOtp;

public class UserOtpMapper {

    public static OtpCode convertToOtp(UserOtp userOtp) {
        OtpCode otpCode = OtpCode.builder().otp(userOtp.getOtp()).userId(userOtp.getUserId()).build();

        return otpCode;
    }

    public static UserOtp convertToUserOtp(OtpCode otpCode) {
        UserOtp userOtp = UserOtp.builder()
                .userId(otpCode.getUserId())
                .otp(otpCode.getOtp())
                .build();

        return userOtp;
    }

}
