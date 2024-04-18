package com.scme.messenger.services;

import com.scme.messenger.dto.userdto.UserDTO;

public interface IUserService {
    UserDTO setUser(UserDTO user);
    void deleteUser(String userId);
    UserDTO updateUser(UserDTO user);
    UserDTO getUser(String userId);
    boolean setTwoStepVerification(String userId);
    boolean turnOffTwoStepVerification(String userId);
}
