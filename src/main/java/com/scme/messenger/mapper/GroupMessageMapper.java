package com.scme.messenger.mapper;

import com.scme.messenger.dto.group.GroupMessageDto;
import com.scme.messenger.model.GroupMessage;
import com.scme.messenger.model.User;
import com.scme.messenger.model.composite.GroupMessageID;
import com.scme.messenger.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class GroupMessageMapper {

    @Autowired
    private static IUserService iUserService;

    public static GroupMessage convertToGroupMessage(GroupMessageDto groupMessageDto){

        User user = UserMapper.convertDtoToUser(new User() , iUserService.getUser(groupMessageDto.getSenderId()));

        return GroupMessage.builder()
                .groupMessageID(GroupMessageID.builder()
                        .courseId(groupMessageDto.getCourseId())
                        .moduleId(groupMessageDto.getModuleId())
                        .build())
                .content(groupMessageDto.getContent())
                .timestamp(groupMessageDto.getTimestamp())
                .user(user)
                .build();
    }

    public static GroupMessageDto convertToGroupMessageDto(GroupMessage groupMessage){
        return GroupMessageDto.builder()
                .senderId(groupMessage.getUser().getUserId())
                .content(groupMessage.getContent())
                .courseId(groupMessage.getGroupMessageID().getCourseId())
                .moduleId(groupMessage.getGroupMessageID().getModuleId())
                .timestamp(groupMessage.getTimestamp())
                .build();
    }

}
