package com.scme.messenger.services;

import com.scme.messenger.dto.course.CourseDto;
import com.scme.messenger.dto.course.CoursePreviewResponseDto;
import com.scme.messenger.dto.course.CourseResponseDto;

import java.util.Set;

public interface ICourseService {
    void create(CourseDto courseDto);
    CourseResponseDto get(String courseId, String moduleId);
    void update(CourseDto courseDto);
    void delete(String courseId, String moduleId);
    Set<CoursePreviewResponseDto> getAllCoursesOfStudent(String studentId);
}
