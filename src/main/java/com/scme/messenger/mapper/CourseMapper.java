package com.scme.messenger.mapper;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.course.CourseDto;
import com.scme.messenger.dto.course.CoursePreviewResponseDto;
import com.scme.messenger.dto.course.CourseResponseDto;
import com.scme.messenger.dto.group.GroupMessageResponseDto;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.model.*;
import com.scme.messenger.model.composite.CourseID;
import com.scme.messenger.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseMapper {

    private final UserRepo userRepo;
    @Value("${messages.limit}")
    private int limitOfMessages;


    public CourseDto getCourseDto(Course course){
        return CourseDto.builder()
                .courseId(course.getCourseID().getCourseId())
                .moduleId(course.getCourseID().getModuleId())
                .instructorId(course.getInstructor().getUserId())
                .name(course.getName())
                .build();
    }

    public Course getCourse(CourseDto courseDto){
        CourseID courseID = CourseID.builder()
                .courseId(courseDto.getCourseId())
                .moduleId(courseDto.getModuleId())
                .build();

        User instructor = Optional.of(userRepo.getReferenceById(courseDto.getInstructorId()))
                .orElseThrow(() -> new BadRequestException(ResponseConstants.USER_NOT_FOUND));


        return Course.builder()
                .courseID(courseID)
                .name(courseDto.getName())
                .instructor(instructor)
                .build();
    }


    @Transactional
    public CourseResponseDto getCourseResponseDto(Course course, int page, int size){
        Set<User> users = course.getUsers();
        Set<GroupMessage> messages = course.getGroupMessages();


        return CourseResponseDto.builder()
                .courseId(course.getCourseID().getCourseId())
                .moduleId(course.getCourseID().getModuleId())
                .instructor(UserMapper.convertUserToUserResponseDto(course.getInstructor()))
                .members(users == null ? 1 : users.size() + 1)
                .users(users.stream()
                        .map(user -> UserMapper.convertUserToUserResponseDto(user))
                        .collect(Collectors.toList())
                )
                .messages(
                        messages.stream()
                                .sorted(Comparator.comparing(GroupMessage::getTimestamp).reversed())
                                .skip(page * limitOfMessages)
                                .limit((page * limitOfMessages) + limitOfMessages)
                                .map(groupMessage -> GroupMessageResponseDto.builder()
                                        .messageId(groupMessage.getMessageId())
                                        .sender(UserMapper.convertUserToUserResponseDto(groupMessage.getUser()))
                                        .content(groupMessage.getContent())
                                        .mime_type(groupMessage.getAttachment() == null ? null : groupMessage.getAttachment().getMime_type())
                                        .filename(groupMessage.getAttachment() == null ? null : groupMessage.getAttachment().getFilename())
                                        .data(groupMessage.getAttachment() == null ? null : groupMessage.getAttachment().getData())
                                        .timestamp(groupMessage.getTimestamp())
                                        .build())
                                .collect(Collectors.toList())
                )
                .stopConversation(course.isStopConversation())
                .name(course.getName())
                .build();
    }

    public CoursePreviewResponseDto getCoursePreviewResponseDto(Course course){
        Set<User> users = course.getUsers();
        List<GroupMessageResponseDto> lastMessage = course.getGroupMessages().stream()
                .sorted(Comparator.comparing(GroupMessage::getTimestamp).reversed())
                .limit(1)
                .map(groupMessage -> GroupMessageResponseDto.builder()
                        .messageId(groupMessage.getMessageId())
                        .sender(UserMapper.convertUserToUserResponseDto(groupMessage.getUser()))
                        .content(groupMessage.getContent())
                        .filename(groupMessage.getAttachment() == null ? null : groupMessage.getAttachment().getFilename())
                        .mime_type(groupMessage.getAttachment() == null ? null : groupMessage.getAttachment().getMime_type())
                        .data(groupMessage.getAttachment() == null ? null : groupMessage.getAttachment().getData())
                        .timestamp(groupMessage.getTimestamp())
                        .build())
                .collect(Collectors.toList());

        return CoursePreviewResponseDto.builder()
                .courseId(course.getCourseID().getCourseId())
                .moduleId(course.getCourseID().getModuleId())
                .instructor(UserMapper.convertUserToUserResponseDto(course.getInstructor()))
                .members(users == null ? 1 : users.size() + 1)
                .name(course.getName())
                .lastMessage(lastMessage.size() == 0 ? null : lastMessage.get(0))
                .build();
    }
}
