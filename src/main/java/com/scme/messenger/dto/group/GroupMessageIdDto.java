package com.scme.messenger.dto.group;


import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessageIdDto {
    @com.scme.messenger.validations.UUID
    private UUID messageId;
    @Pattern(regexp = "^[1-9]$", message = "Course Id must be greater than zero")
    private String courseId;
    @Pattern(regexp = "^\\d{6}$", message = "Module Id must be 6 digits")
    private String moduleId;
    @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits")
    private String senderId;
}
