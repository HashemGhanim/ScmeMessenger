package com.scme.messenger.mapper;

import com.scme.messenger.dto.group.GroupMessageDto;
import com.scme.messenger.dto.group.SenderGroupMessageDto;
import com.scme.messenger.dto.userdto.SenderDto;
import com.scme.messenger.model.Course;
import com.scme.messenger.model.GroupMessage;
import com.scme.messenger.model.GroupMessageAttachment;
import com.scme.messenger.model.User;
import com.scme.messenger.model.composite.CourseID;
import com.scme.messenger.repository.CourseRepo;
import com.scme.messenger.repository.UserRepo;
import com.scme.messenger.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupMessageMapper {

    private final UserRepo userRepo;
    private final CourseRepo courseRepo;

    public GroupMessage convertToGroupMessage(GroupMessageDto groupMessageDto){

        User user = userRepo.getReferenceById(groupMessageDto.getSenderId());
        Course course = courseRepo.getReferenceById(CourseID.builder()
                        .courseId(groupMessageDto.getCourseId())
                        .moduleId(groupMessageDto.getModuleId())
                .build());

        return GroupMessage.builder()
                .courseId(groupMessageDto.getCourseId())
                .moduleId(groupMessageDto.getModuleId())
                .content(groupMessageDto.getContent())
                .timestamp(groupMessageDto.getTimestamp())
                .iv(groupMessageDto.getIv())
                .user(user)
                .course(course)
                .build();
    }

    public  GroupMessageDto convertToGroupMessageDto(GroupMessage groupMessage){
        return GroupMessageDto.builder()
                .senderId(groupMessage.getUser().getUserId())
                .content(groupMessage.getContent())
                .courseId(groupMessage.getCourseId())
                .moduleId(groupMessage.getModuleId())
                .timestamp(groupMessage.getTimestamp())
                .iv(groupMessage.getIv())
                .build();
    }

    public  SenderGroupMessageDto convertToSenderGroupMessageDto(GroupMessage groupMessage){
        GroupMessageAttachment attachment = groupMessage.getAttachment();
        return SenderGroupMessageDto.builder()
                .messageId(groupMessage.getMessageId())
                .courseId(groupMessage.getCourseId())
                .moduleId(groupMessage.getModuleId())
                .iv(groupMessage.getIv())
                .sender(UserMapper.convertUserToSenderDto(groupMessage.getUser()))
                .content(groupMessage.getContent())
                .filename(attachment == null ? null : attachment.getFilename())
                .mime_type(attachment == null ? null : attachment.getMime_type())
                .data(attachment == null ? null : attachment.getData())
                .build();
    }

}
