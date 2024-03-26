package com.scme.messenger.mapper;

import com.scme.messenger.dto.UserDTO;
import com.scme.messenger.model.User;

public class UserMapper {

    public static UserDTO convertUserToDTO(User user, UserDTO userDTO) {
        userDTO.setUserId(user.getUserId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }

    public static User convertDtoToUser(User user, UserDTO userDTO) {
        user.setUserId(userDTO.getUserId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
