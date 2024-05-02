package com.scme.messenger.services;

import com.scme.messenger.dto.group.GroupMessageDto;

import java.util.Set;

public interface IGroupMessageService {
    GroupMessageDto save(GroupMessageDto groupMessageDto);
    Set<GroupMessageDto> getAllGroupMessages(String courseId , String moduleId);
}
