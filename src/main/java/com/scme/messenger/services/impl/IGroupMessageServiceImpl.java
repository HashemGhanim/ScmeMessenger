package com.scme.messenger.services.impl;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.group.GroupMessageDto;
import com.scme.messenger.dto.group.GroupMessageIdDto;
import com.scme.messenger.dto.group.SenderGroupMessageDto;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.mapper.GroupMessageMapper;
import com.scme.messenger.model.GroupMessage;
import com.scme.messenger.model.GroupMessageAttachment;
import com.scme.messenger.repository.GroupMessageRepo;
import com.scme.messenger.services.IGroupMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


@RequiredArgsConstructor
@Service
public class IGroupMessageServiceImpl implements IGroupMessageService {

    private final GroupMessageRepo groupMessageRepo;
    private final GroupMessageMapper groupMessageMapper;

    @Override
    public SenderGroupMessageDto save(GroupMessageDto groupMessageDto) {
        GroupMessage groupMessage = groupMessageMapper.convertToGroupMessage(groupMessageDto);

        GroupMessageAttachment attachment = GroupMessageAttachment.builder()
                .filename(groupMessageDto.getFilename())
                .mime_type(groupMessageDto.getMime_type())
                .data(groupMessageDto.getData())
                .groupMessage(groupMessage)
                .build();

        groupMessage.setAttachment(attachment);

        GroupMessage res = groupMessageRepo.save(groupMessage);

        return groupMessageMapper.convertToSenderGroupMessageDto(res);
    }

    @Override
    public void delete(GroupMessageIdDto messageIdDto) {
        if(!groupMessageRepo.existsById(messageIdDto.getMessageId()))
            throw new BadRequestException(ResponseConstants.MESSAGE_NOT_FOUND);

        groupMessageRepo.deleteById(messageIdDto.getMessageId());
    }
}
