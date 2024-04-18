package com.scme.messenger.services.impl;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.userdto.UserDTO;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.mapper.UserMapper;
import com.scme.messenger.model.User;
import com.scme.messenger.repository.UserRepo;
import com.scme.messenger.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IUserServiceImpl implements IUserService {

    private final UserRepo userRepo;

    @Override
    public UserDTO setUser(UserDTO user) {

        if (userRepo.isUserExist(user.getUserId()))
            throw new BadRequestException(ResponseConstants.USER_IS_ALREADY_EXIST);

        User tempUser = UserMapper.convertDtoToUser(new User(), user);

        userRepo.save(tempUser);

        return UserMapper.convertUserToDTO(tempUser, user);
    }

    @Override
    public void deleteUser(String userId) {
        User user = Optional.of(userRepo.getReferenceById(userId))
                .orElseThrow(() -> new BadRequestException(ResponseConstants.USER_NOT_FOUND));

        userRepo.delete(user);
    }

    @Override
    public UserDTO updateUser(UserDTO user) {
        User tempUser = Optional.of(userRepo.getReferenceById(user.getUserId()))
                .orElseThrow(() -> new BadRequestException(ResponseConstants.USER_NOT_FOUND));

        tempUser = UserMapper.convertDtoToUser(tempUser, user);

        userRepo.save(tempUser);

        return user;
    }

    @Override
    public UserDTO getUser(String userId) {
        User user = Optional.of(userRepo.getReferenceById(userId))
                .orElseThrow(() -> new BadRequestException(ResponseConstants.USER_NOT_FOUND));

        UserDTO userDTO = UserMapper.convertUserToDTO(user, new UserDTO());

        return userDTO;
    }

    @Override
    public boolean setTwoStepVerification(String userId) {

        User user = Optional.of(userRepo.getReferenceById(userId)).orElseThrow(
                () -> new BadRequestException(ResponseConstants.USER_NOT_FOUND));

        user.setTwoStepVerify(true);

        userRepo.save(user);

        return true;
    }

    @Override
    public boolean turnOffTwoStepVerification(String userId) {

        User user = Optional.of(userRepo.getReferenceById(userId)).orElseThrow(
                () -> new BadRequestException(ResponseConstants.USER_NOT_FOUND));

        user.setTwoStepVerify(false);

        userRepo.save(user);

        return true;
    }

}
