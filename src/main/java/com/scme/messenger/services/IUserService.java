package com.scme.messenger.services;

import com.scme.messenger.dto.userdto.BlockUserDto;
import com.scme.messenger.dto.userdto.UserDTO;

import java.util.List;
import java.util.Set;

public interface IUserService {
    UserDTO setUser(UserDTO user);
    void deleteUser(String userId);
    UserDTO updateUser(UserDTO user);
    UserDTO getUser(String userId);
    boolean setTwoStepVerification(String userId);
    boolean turnOffTwoStepVerification(String userId);
    boolean blockUser(BlockUserDto blockUserDto);
    boolean unBlockUser(BlockUserDto blockUserDto);
    Set<UserDTO> searchUsers(String username , String userId);

    List<UserDTO> getAllUsers();
}
