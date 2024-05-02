package com.scme.messenger.services.impl;

import com.scme.messenger.dto.group.GroupMessageDto;
import com.scme.messenger.mapper.GroupMessageMapper;
import com.scme.messenger.model.GroupMessage;
import com.scme.messenger.repository.GroupMessageRepo;
import com.scme.messenger.services.IGroupMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


@RequiredArgsConstructor
@Service
public class IGroupMessageServiceImpl implements IGroupMessageService {

    private final GroupMessageRepo groupMessageRepo;

    @Override
    public GroupMessageDto save(GroupMessageDto groupMessageDto) {
        GroupMessage groupMessage = GroupMessageMapper.convertToGroupMessage(groupMessageDto);

        GroupMessage res = groupMessageRepo.save(groupMessage);

        return GroupMessageMapper.convertToGroupMessageDto(res);
    }

    @Override
    public Set<GroupMessageDto> getAllGroupMessages(String courseId, String moduleId) {
        return null;
    }
}
