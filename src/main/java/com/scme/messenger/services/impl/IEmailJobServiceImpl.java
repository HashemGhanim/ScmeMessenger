package com.scme.messenger.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.jobs.EmailJob;
import com.scme.messenger.repository.UserRepo;
import com.scme.messenger.services.IEmailJobService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class IEmailJobServiceImpl implements IEmailJobService {

    @Value("${zone.properties.zone-id}")
    private String zonoId;

    private final UserRepo userRepo;

    private final Scheduler scheduler;

    @Override
    public void scheduleJobs(LocalDateTime localDateTime, String userId) throws SchedulerException {

        ZonedDateTime dateTime = ZonedDateTime.of(localDateTime, ZoneId.of(zonoId));

        dateTime = dateTime.plusMinutes(1);
        if (dateTime.isBefore(ZonedDateTime.now())) {
            log.info(dateTime.now().toString());
            log.info(ZonedDateTime.now().toString());
            // return;
        }

        JobDetail jobDetail = jobDetail(userId);
        Trigger trigger = triggerBuilder(jobDetail, dateTime);

        scheduler.scheduleJob(jobDetail, trigger);
    }

    private JobDetail jobDetail(String userId) {

        final String userEmail = Optional.of(userRepo.getReferenceById(userId).getEmail())
                .orElseThrow(() -> new BadRequestException(ResponseConstants.USER_NOT_FOUND));

        final String userName = Optional.of(userRepo.getReferenceById(userId).getName())
                .orElseThrow(() -> new BadRequestException(ResponseConstants.USER_NOT_FOUND));

        JobDataMap map = new JobDataMap();
        map.put("userId", userId);
        map.put("userEmail", userEmail);
        map.put("userName", userName);

        return JobBuilder.newJob(EmailJob.class)
                .usingJobData(map)
                .withIdentity(UUID.randomUUID().toString(), "email-job")
                .storeDurably()
                .build();
    }

    private Trigger triggerBuilder(JobDetail jobDetail, ZonedDateTime dateTime) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-trigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .startAt(Date.from(dateTime.plusSeconds(15).toInstant()))
                .build();
    }
}
