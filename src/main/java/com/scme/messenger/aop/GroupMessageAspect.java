package com.scme.messenger.aop;

import com.scme.messenger.dto.group.GroupMessageDto;
import com.scme.messenger.model.Course;
import com.scme.messenger.model.composite.CourseID;
import com.scme.messenger.repository.CourseRepo;
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
public class GroupMessageAspect {

    private final CourseRepo courseRepo;

    @Pointcut("@annotation(com.scme.messenger.validations.AuthorizeSentGroupMessage)")
    public void sendGroupMessageAllow(){}

    @Around("sendGroupMessageAllow() && args(groupMessageDto)")
    public Object checkIsAllowed(ProceedingJoinPoint joinPoint, GroupMessageDto groupMessageDto) throws Throwable{
        CourseID courseID = CourseID.builder()
                .courseId(groupMessageDto.getCourseId())
                .moduleId(groupMessageDto.getModuleId())
                .build();

        if(!courseRepo.existsById(courseID))
            return null;


        Course course = courseRepo.getReferenceById(courseID);

        if(course.isStopConversation() && !groupMessageDto.getSenderId().equals(course.getInstructor().getUserId()))
            return null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(course.isStopConversation() && !authentication.getName().equals(groupMessageDto.getSenderId()))
            return null;

        return joinPoint.proceed();
    }
}
