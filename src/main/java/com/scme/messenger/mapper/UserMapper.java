package com.scme.messenger.mapper;

import com.scme.messenger.constants.Role;
import com.scme.messenger.dto.userdto.AuthenticatedUserDto;
import com.scme.messenger.dto.userdto.SenderDto;
import com.scme.messenger.dto.userdto.UserDTO;
import com.scme.messenger.dto.userdto.UserResponseDto;
import com.scme.messenger.encryption.util.PrivateKey;
import com.scme.messenger.encryption.util.PublicKey;
import com.scme.messenger.model.User;

public class UserMapper {

    public static UserDTO convertUserToDTO(User user, UserDTO userDTO) {
        userDTO.setUserId(user.getUserId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());

        if(user.getRole() == Role.ADMIN)
            userDTO.setRole(2);
        else if(user.getRole() == Role.DOCTOR)
            userDTO.setRole(1);
        else
            userDTO.setRole(0);

        return userDTO;
    }

    public static User convertDtoToUser(User user, UserDTO userDTO) {
        user.setUserId(userDTO.getUserId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        if(userDTO.getRole() == 2)
            user.setRole(Role.ADMIN);
        else if(userDTO.getRole() == 1)
            user.setRole(Role.DOCTOR);
        else
            user.setRole(Role.STUDENT);

        return user;
    }


    public static AuthenticatedUserDto convertUserToAuthUserDto(User user){


        AuthenticatedUserDto authenticatedUserDto = AuthenticatedUserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .build();



        if(user.getRole().equals(Role.ADMIN))
            authenticatedUserDto.setRole(2);
        else if(user.getRole().equals(Role.DOCTOR))
            authenticatedUserDto.setRole(1);
        else
            authenticatedUserDto.setRole(0);

        return authenticatedUserDto;
    }


    public static SenderDto convertUserToSenderDto(User user){
        SenderDto senderDto = SenderDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        if(user.getRole().equals(Role.ADMIN))
            senderDto.setRole(2);
        else if(user.getRole().equals(Role.DOCTOR))
            senderDto.setRole(1);
        else
            senderDto.setRole(0);

        return senderDto;
    }

    public static UserResponseDto convertUserToUserResponseDto(User user){
        UserResponseDto userDto = UserResponseDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        if(user.getRole().equals(Role.ADMIN))
            userDto.setRole(2);
        else if(user.getRole().equals(Role.DOCTOR))
            userDto.setRole(1);
        else
            userDto.setRole(0);

        return userDto;
    }
}
