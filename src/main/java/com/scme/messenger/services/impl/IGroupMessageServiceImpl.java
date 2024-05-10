package com.scme.messenger.services.impl;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.constants.Role;
import com.scme.messenger.dto.group.*;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.mapper.GroupMessageMapper;
import com.scme.messenger.mapper.UserMapper;
import com.scme.messenger.model.GroupMessage;
import com.scme.messenger.model.GroupMessageAttachment;
import com.scme.messenger.model.User;
import com.scme.messenger.repository.GroupMessageRepo;
import com.scme.messenger.repository.UserRepo;
import com.scme.messenger.services.IGroupMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class IGroupMessageServiceImpl implements IGroupMessageService {

    private final GroupMessageRepo groupMessageRepo;
    private final GroupMessageMapper groupMessageMapper;
    private final UserRepo userRepo;

    @Override
    public SenderGroupMessageDto save(GroupMessageDto groupMessageDto) {
        GroupMessage groupMessage = groupMessageMapper.convertToGroupMessage(groupMessageDto);

        GroupMessageAttachment attachment = GroupMessageAttachment.builder()
                .filename(groupMessageDto.getFilename())
                .mime_type(groupMessageDto.getMime_type())
                .data(groupMessageDto.getData())
                .build();

        if(groupMessageDto.getData().length() > 0) {
            attachment.setGroupMessage(groupMessage);
            groupMessage.setAttachment(attachment);
        }
        GroupMessage res = groupMessageRepo.save(groupMessage);

        return groupMessageMapper.convertToSenderGroupMessageDto(res);
    }

    @Override
    public void delete(GroupMessageIdDto messageIdDto) {
        if(!groupMessageRepo.existsById(messageIdDto.getMessageId()))
            throw new BadRequestException(ResponseConstants.MESSAGE_NOT_FOUND);

        groupMessageRepo.deleteById(messageIdDto.getMessageId());
    }

    @Override
    public void pinMessage(GroupMessageIdPinDto groupMessageIdPinDto) {
        if(!groupMessageRepo.existsById(groupMessageIdPinDto.getMessageId()))
            throw new BadRequestException(ResponseConstants.MESSAGE_NOT_FOUND);

        GroupMessage groupMessage = groupMessageRepo.getReferenceById(groupMessageIdPinDto.getMessageId());

        groupMessage.setPinned(true);

        groupMessageRepo.save(groupMessage);
    }

    @Transactional
    @Override
    public List<GroupMessagePinResponseDto> getPinnedMessages(String userId) {
        if(!userRepo.isUserExist(userId))
            throw new BadRequestException(ResponseConstants.USER_NOT_FOUND);

        User user = userRepo.getReferenceById(userId);
        List<GroupMessage> messages;
        if(user.getRole().equals(Role.ADMIN))
            return null;
        else if(user.getRole().equals(Role.DOCTOR)){
            messages = user.getManages().stream()
                    .flatMap(course -> course.getGroupMessages().stream())
                    .filter(message -> message.isPinned())
                    .collect(Collectors.toList());
        }else{
            messages = user.getCourses().stream()
                    .flatMap(course -> course.getGroupMessages().stream())
                    .filter(message -> message.isPinned())
                    .collect(Collectors.toList());
        }

        return messages.stream()
                .map(message -> GroupMessagePinResponseDto.builder()
                        .messageId(message.getMessageId())
                        .sender(UserMapper.convertUserToUserResponseDto(message.getUser()))
                        .group(
                                GroupDto.builder()
                                        .courseId(message.getCourseId())
                                        .moduleId(message.getModuleId())
                                        .name(message.getCourse() == null ? null : message.getCourse().getName())
                                        .secretKey(message.getCourse() == null ? null : message.getCourse().getSecretKey())
                                        .build()
                        )
                        .iv(message.getIv())
                        .content(message.getContent())
                        .mime_type(message.getAttachment() == null ? null : message.getAttachment().getMime_type())
                        .filename(message.getAttachment() == null ? null : message.getAttachment().getFilename())
                        .data(message.getAttachment() == null ? null : message.getAttachment().getData())
                        .timestamp(message.getTimestamp())
                        .build())
                .collect(Collectors.toList());
    }
}
