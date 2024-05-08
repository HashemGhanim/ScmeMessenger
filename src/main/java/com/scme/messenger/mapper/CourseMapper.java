package com.scme.messenger.mapper;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.course.CourseDto;
import com.scme.messenger.dto.course.CoursePreviewResponseDto;
import com.scme.messenger.dto.course.CourseResponseDto;
import com.scme.messenger.dto.group.GroupMessageResponseDto;
import com.scme.messenger.dto.userdto.UserDTO;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.model.*;
import com.scme.messenger.model.composite.CourseID;
import com.scme.messenger.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
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
    public CourseResponseDto getCourseResponseDto(Course course){
        Set<User> users = course.getUsers();
        Set<GroupMessage> messages = course.getGroupMessages();


        return CourseResponseDto.builder()
                .courseId(course.getCourseID().getCourseId())
                .moduleId(course.getCourseID().getModuleId())
                .instructor(UserMapper.convertUserToDTO(course.getInstructor(), new UserDTO()))
                .members(users == null ? 0 : users.size())
                .users(users.stream()
                        .map(user -> UserMapper.convertUserToDTO(user , new UserDTO()))
                        .collect(Collectors.toList())
                )
                .messages(
                        messages.stream()
                                .sorted(Comparator.comparing(GroupMessage::getTimestamp).reversed())
                                .limit(limitOfMessages)
                                .map(groupMessage -> GroupMessageResponseDto.builder()
                                        .senderId(groupMessage.getUser().getUserId())
                                        .content(groupMessage.getContent())
                                        .mime_type(groupMessage.getAttachment() == null ? null : groupMessage.getAttachment().getMime_type())
                                        .filename(groupMessage.getAttachment() == null ? null : groupMessage.getAttachment().getFilename())
                                        .data(groupMessage.getAttachment() == null ? null : groupMessage.getAttachment().getData())
                                        .timestamp(groupMessage.getTimestamp())
                                        .build())
                                .collect(Collectors.toList())
                )
                .name(course.getName())
                .build();
    }

    public CoursePreviewResponseDto getCoursePreviewResponseDto(Course course){
        Set<User> users = course.getUsers();
        return CoursePreviewResponseDto.builder()
                .courseId(course.getCourseID().getCourseId())
                .moduleId(course.getCourseID().getModuleId())
                .instructor(UserMapper.convertUserToDTO(course.getInstructor(), new UserDTO()))
                .members(users == null ? 0 : users.size())
                .name(course.getName())
                .build();
    }
}
