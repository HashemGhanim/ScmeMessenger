package com.scme.messenger.mapper;

import com.scme.messenger.constants.Role;
import com.scme.messenger.dto.userdto.UserDTO;
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
}
