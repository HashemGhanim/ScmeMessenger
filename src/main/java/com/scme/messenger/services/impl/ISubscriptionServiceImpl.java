package com.scme.messenger.services.impl;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.constants.Role;
import com.scme.messenger.dto.subscription.SubscriptionDto;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.model.Course;
import com.scme.messenger.model.User;
import com.scme.messenger.model.composite.CourseID;
import com.scme.messenger.repository.CourseRepo;
import com.scme.messenger.repository.UserRepo;
import com.scme.messenger.services.ISubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ISubscriptionServiceImpl implements ISubscriptionService {

    private final CourseRepo courseRepo;
    private final UserRepo userRepo;

    @Transactional
    @Override
    public void subscribe(SubscriptionDto subscriptionDto) {
        CourseID courseID = getCourseId(subscriptionDto.getCourseId(), subscriptionDto.getModuleId());

        Course course = Optional.of(courseRepo.getReferenceById(courseID))
                .orElseThrow(() -> new BadRequestException(ResponseConstants.COURSE_NOT_FOUND));

        User user = Optional.of(userRepo.getReferenceById(subscriptionDto.getUserId()))
                .orElseThrow(() -> new BadRequestException(ResponseConstants.USER_NOT_FOUND));

        if(!user.getRole().equals(Role.STUDENT))
            throw new BadRequestException(ResponseConstants.USER_IS_NOT_STUDENT);

        if(courseRepo.isStudentSubscribed(subscriptionDto.getModuleId() , subscriptionDto.getUserId()))
            throw new BadRequestException(ResponseConstants.STUDENT_IS_ALREADY_SUBSCRIBED);

        course.getUsers().add(user);
        user.getCourses().add(course);

        userRepo.save(user);
        courseRepo.save(course);
    }

    @Transactional
    @Override
    public void unSubscribe(SubscriptionDto subscriptionDto) {

        CourseID courseID = getCourseId(subscriptionDto.getCourseId(), subscriptionDto.getModuleId());

        Course course = Optional.of(courseRepo.getReferenceById(courseID))
                .orElseThrow(() -> new BadRequestException(ResponseConstants.COURSE_NOT_FOUND));

        User user = Optional.of(userRepo.getReferenceById(subscriptionDto.getUserId()))
                .orElseThrow(() -> new BadRequestException(ResponseConstants.USER_NOT_FOUND));

        course.getUsers().remove(user);
        user.getCourses().remove(course);

        userRepo.save(user);
        courseRepo.save(course);
    }

    private CourseID getCourseId(String courseId , String moduleId){
        return CourseID.builder()
                .courseId(courseId)
                .moduleId(moduleId)
                .build();
    }
}
