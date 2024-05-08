package com.scme.messenger.dto.course;

import com.scme.messenger.dto.group.GroupMessageDto;
import com.scme.messenger.dto.group.GroupMessageResponseDto;
import com.scme.messenger.dto.userdto.UserDTO;
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
    private UserDTO instructor;
    private String name;
    private List<UserDTO> users;
    private List<GroupMessageResponseDto> messages;
    private int members;
}
