package com.scme.messenger.services.impl;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.constants.Role;
import com.scme.messenger.dto.course.CourseDto;
import com.scme.messenger.dto.course.CourseIdDto;
import com.scme.messenger.dto.course.CoursePreviewResponseDto;
import com.scme.messenger.dto.course.CourseResponseDto;
import com.scme.messenger.encryption.AesEncryptionGenerator;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.mapper.CourseMapper;
import com.scme.messenger.model.Course;
import com.scme.messenger.model.User;
import com.scme.messenger.model.composite.CourseID;
import com.scme.messenger.repository.CourseRepo;
import com.scme.messenger.repository.UserRepo;
import com.scme.messenger.services.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ICourseServiceImpl implements ICourseService {

    private final CourseRepo courseRepo;
    private final CourseMapper courseMapper;
    private final UserRepo userRepo;

    @Override
    public void create(CourseDto courseDto) throws Exception {

        Course course = courseMapper.getCourse(courseDto);

        if(courseRepo.existsById(course.getCourseID()))
            throw new BadRequestException(ResponseConstants.COURSE_IS_EXIST);

        if(!course.getInstructor().getRole().equals(Role.DOCTOR))
            throw new BadRequestException(ResponseConstants.USER_IS_NOT_INSTRUCTOR);

        courseRepo.save(course);
    }

    @Override
    public CourseResponseDto get(String courseId, String moduleId, int page, int size) {
        CourseID courseID = CourseID.builder()
                .moduleId(moduleId)
                .courseId(courseId)
                .build();

        Course course = Optional.of(courseRepo.getReferenceById(courseID))
                .orElseThrow(()-> new BadRequestException(ResponseConstants.COURSE_NOT_FOUND));

        return courseMapper.getCourseResponseDto(course, page, size);
    }

    @Override
    public void update(CourseDto courseDto) throws Exception {
        CourseID courseID = CourseID.builder()
                .moduleId(courseDto.getModuleId())
                .courseId(courseDto.getCourseId())
                .build();

        Course course = Optional.of(courseRepo.getReferenceById(courseID))
                .orElseThrow(()-> new BadRequestException(ResponseConstants.COURSE_NOT_FOUND));

        Course tempCourse = courseMapper.getCourse(courseDto);

        if(!tempCourse.getInstructor().getRole().equals(Role.DOCTOR))
            throw new BadRequestException(ResponseConstants.USER_IS_NOT_INSTRUCTOR);

        course.setInstructor(tempCourse.getInstructor());
        course.setName(tempCourse.getName());

        courseRepo.save(course);
    }

    @Override
    public void delete(String courseId, String moduleId) {
        CourseID courseID = CourseID.builder()
                .moduleId(moduleId)
                .courseId(courseId)
                .build();

        Course course = Optional.of(courseRepo.getReferenceById(courseID))
                .orElseThrow(()-> new BadRequestException(ResponseConstants.COURSE_NOT_FOUND));

        courseRepo.delete(course);
    }

    @Override
    public Set<CoursePreviewResponseDto> getAllCoursesOfUser(String userId) {
        User user = Optional.of(userRepo.getReferenceById(userId))
                .orElseThrow(()-> new BadRequestException(ResponseConstants.USER_NOT_FOUND));
        Set<Course> courses;

        if(user.getRole().equals(Role.ADMIN))
            return null;
        else if(user.getRole().equals(Role.DOCTOR)){
            courses = user.getCourses();
        }else{
            courses = courseRepo.findAllByStudentId(userId);
        }
        Set<CoursePreviewResponseDto> userCourses = courses.stream()
                .map(course -> courseMapper.getCoursePreviewResponseDto(course))
                .collect(Collectors.toSet());

        return userCourses;
    }

    @Override
    public void stopConversationOfGroup(CourseIdDto courseIdDto) {
        CourseID courseID = CourseID.builder()
                .courseId(courseIdDto.getCourseId())
                .moduleId(courseIdDto.getModuleId())
                .build();

        if(!courseRepo.existsById(courseID))
            throw new BadRequestException(ResponseConstants.COURSE_NOT_FOUND);

        Course course = courseRepo.getReferenceById(courseID);

        course.setStopConversation(true);

        courseRepo.save(course);
    }

    @Override
    public void allowConversationOfGroup(CourseIdDto courseIdDto) {
        CourseID courseID = CourseID.builder()
                .courseId(courseIdDto.getCourseId())
                .moduleId(courseIdDto.getModuleId())
                .build();

        if(!courseRepo.existsById(courseID))
            throw new BadRequestException(ResponseConstants.COURSE_NOT_FOUND);

        Course course = courseRepo.getReferenceById(courseID);

        course.setStopConversation(false);

        courseRepo.save(course);
    }


}
