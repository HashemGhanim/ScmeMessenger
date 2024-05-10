package com.scme.messenger.dto.group;

import com.scme.messenger.dto.userdto.UserResponseDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private String courseId;
    private String moduleId;
    private String secretKey;
    private String name;
}
