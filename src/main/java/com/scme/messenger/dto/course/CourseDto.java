package com.scme.messenger.dto.course;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    @Pattern(regexp = "^[1-9]$", message = "Course Id must be greater than zero")
    private String courseId;
    @Pattern(regexp = "^\\d{6}$", message = "Module Id must be 6 digits")
    private String moduleId;
    @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits")
    private String instructorId;
    @NotEmpty(message = "Name of course should not be empty, Please try again ...")
    private String name;
}
