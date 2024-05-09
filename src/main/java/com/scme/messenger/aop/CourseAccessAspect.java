package com.scme.messenger.aop;

import com.scme.messenger.constants.Role;
import com.scme.messenger.model.Course;
import com.scme.messenger.model.User;
import com.scme.messenger.model.composite.CourseID;
import com.scme.messenger.repository.CourseRepo;
import com.scme.messenger.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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

    @Around("courseAccessPointcut() && args(courseId, moduleId)")
    public Object checkCourseAccess(ProceedingJoinPoint joinPoint, String courseId, String moduleId) throws Throwable{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            return null;
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
            return null;
        }

        return joinPoint.proceed();
    }

}
