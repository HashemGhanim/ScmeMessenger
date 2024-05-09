package com.scme.messenger.dto.course;

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
public class CoursePreviewResponseDto {
    private String courseId;
    private String moduleId;
    private UserResponseDto instructor;
    private String name;
    private GroupMessageResponseDto lastMessage;
    private int members;
}
