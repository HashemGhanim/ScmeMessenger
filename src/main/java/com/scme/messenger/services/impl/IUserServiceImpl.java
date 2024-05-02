package com.scme.messenger.services.impl;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.userdto.BlockUserDto;
import com.scme.messenger.dto.userdto.UserDTO;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.mapper.UserMapper;
import com.scme.messenger.model.User;
import com.scme.messenger.repository.UserRepo;
import com.scme.messenger.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public boolean blockUser(BlockUserDto blockUserDto) {

        User user = userRepo.getReferenceById(blockUserDto.getSenderId());
        User blockedUser = userRepo.getReferenceById(blockUserDto.getRecepientId());

        user.getBlocks().add(blockedUser);
        blockedUser.getBlocked().add(user);

        userRepo.save(user);
        userRepo.save(blockedUser);

        return true;
    }

    @Override
    public boolean unBlockUser(BlockUserDto blockUserDto) {

        User user = userRepo.getReferenceById(blockUserDto.getSenderId());
        User blockedUser = userRepo.getReferenceById(blockUserDto.getRecepientId());

        user.getBlocks().remove(blockedUser);
        blockedUser.getBlocked().remove(user);

        userRepo.save(user);
        userRepo.save(blockedUser);

        return true;
    }

    @Override
    public Set<UserDTO> searchUsers(String username , String userId) {

        Set<User> users = userRepo.findUserByPartOfName(username);
        User currentUser = userRepo.getReferenceById(userId);

        Set<User> filteredUsers = users.stream()
                .filter(user -> !currentUser.getBlocks().contains(user))
                .collect(Collectors.toSet());

        Set<UserDTO> finalUsers = filteredUsers.stream()
                .map(user -> UserMapper.convertUserToDTO(user , new UserDTO()))
                .collect(Collectors.toSet());

        return finalUsers;
    }

}
