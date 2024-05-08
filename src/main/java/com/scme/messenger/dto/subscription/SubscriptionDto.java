package com.scme.messenger.dto.subscription;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits")
    private String userId;
    @Pattern(regexp = "^[1-9]$", message = "Course Id must be greater than zero")
    private String courseId;
    @Pattern(regexp = "^\\d{6}$", message = "Module Id must be 6 digits")
    private String moduleId;
}
