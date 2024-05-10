package com.scme.messenger.aop;

import com.scme.messenger.dto.group.GroupMessageDto;
import com.scme.messenger.model.Course;
import com.scme.messenger.model.composite.CourseID;
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
public class PrivateMessageAspect {

    @Pointcut("@annotation(com.scme.messenger.validations.AuthorizeSendPrivateMessage)")
    public void sendPrivateMessageAllow(){}

    @Around("sendPrivateMessageAllow() && args(senderId)")
    public Object checkIsAllowed(ProceedingJoinPoint joinPoint, String senderId) throws Throwable{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.getName().equals(senderId))
            return null;

        return joinPoint.proceed();
    }
}
