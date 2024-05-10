package com.scme.messenger.dto.course;

import com.scme.messenger.dto.group.GroupMessageDto;
import com.scme.messenger.dto.group.GroupMessageResponseDto;
import com.scme.messenger.dto.userdto.UserDTO;
import com.scme.messenger.dto.userdto.UserResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {
    private String courseId;
    private String moduleId;
    private String secretKey;
    private UserResponseDto instructor;
    private String name;
    private List<UserResponseDto> users;
    private List<GroupMessageResponseDto> messages;
    private int members;
    private boolean stopConversation;
}
