package com.scme.messenger.aop;

import com.scme.messenger.constants.Role;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.model.Course;
import com.scme.messenger.model.User;
import com.scme.messenger.model.composite.CourseID;
import com.scme.messenger.repository.CourseRepo;
import com.scme.messenger.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CourseAccessAspect {

    private final CourseRepo courseRepo;
    private final UserRepo userRepo;

    @Pointcut("@annotation(com.scme.messenger.validations.AuthorizeCourseAccess)")
    public void courseAccessPointcut(){}

    @Before("courseAccessPointcut() && args(courseId, moduleId)")
    public void checkCourseAccess(JoinPoint joinPoint, String courseId, String moduleId) throws Throwable{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            throw new SecurityException("User is not authenticated");
        }

        final String authenticatedUserId = authentication.getName();

        Course course = courseRepo.getReferenceById(
                CourseID.builder()
                        .courseId(courseId)
                        .moduleId(moduleId)
                        .build()
        );

        User user = userRepo.getReferenceById(authenticatedUserId);
        boolean allowed = course.getInstructor().getUserId().equals(authenticatedUserId) || course.getUsers().contains(user) || user.getRole().equals(Role.ADMIN);
        if (!allowed) {
            throw new BadRequestException("User does not have access to the course: " + courseId);
        }

    }

}
