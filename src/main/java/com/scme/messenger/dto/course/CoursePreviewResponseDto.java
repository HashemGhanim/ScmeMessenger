package com.scme.messenger.dto.course;

import com.scme.messenger.dto.userdto.UserDTO;
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
    private UserDTO instructor;
    private String name;
    private int members;
}
