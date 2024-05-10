package com.scme.messenger.services;

import com.scme.messenger.dto.course.CourseDto;
import com.scme.messenger.dto.course.CourseIdDto;
import com.scme.messenger.dto.course.CoursePreviewResponseDto;
import com.scme.messenger.dto.course.CourseResponseDto;

import java.util.Set;

public interface ICourseService {
    void create(CourseDto courseDto) throws Exception;
    CourseResponseDto get(String courseId, String moduleId, int page, int size);
    void update(CourseDto courseDto) throws Exception;
    void delete(String courseId, String moduleId);
    Set<CoursePreviewResponseDto> getAllCoursesOfUser(String userId);
    void stopConversationOfGroup(CourseIdDto courseIdDto);
    void allowConversationOfGroup(CourseIdDto courseIdDto);
}
