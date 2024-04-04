package com.scme.messenger.services.impl;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.scme.messenger.jobs.OtpJob;
import com.scme.messenger.model.UserOtp;
import com.scme.messenger.repository.UserOtpRepo;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scme.messenger.services.IOTPService;

@RequiredArgsConstructor
@Service
public class IOTPServiceImpl implements IOTPService {

    @Value("${otp.properties.length}")
    private Integer length;

    @Value("${otp.properties.charset}")
    private String charSet;

    @Value("${zone.properties.zone-id}")
    private String zoneId;

    private final UserOtpRepo userOtpRepo;

    private final Scheduler scheduler;

    @Override
    public String generateOTP(String userId) throws SchedulerException {

        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charSet.length());

            otp.append(charSet.charAt(index));
        }

        UserOtp userOtp = UserOtp.builder()
                .userId(userId)
                .otp(otp.toString())
                .build();

        userOtpRepo.save(userOtp);
        schedule(LocalDateTime.now(), userId, otp.toString());

        return otp.toString();
    }

    private void schedule(LocalDateTime localDateTime, String userId, String otp) throws SchedulerException {
        ZonedDateTime dateTime = ZonedDateTime.of(localDateTime, ZoneId.of(zoneId));
        dateTime = dateTime.plusMinutes(5);

        JobDetail jobDetail = buildJobDetail(userId, otp);
        Trigger trigger = buildTrigger(jobDetail, dateTime);

        scheduler.scheduleJob(jobDetail, trigger);
    }

    private JobDetail buildJobDetail(String userId, String otp) {
        JobDataMap map = new JobDataMap();

        map.put("userId", userId);
        map.put("userOtp", otp);

        return JobBuilder.newJob(OtpJob.class)
                .usingJobData(map)
                .withIdentity(UUID.randomUUID().toString(), "otp-job")
                .storeDurably()
                .build();
    }

    private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime dateTime) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobDetail.getKey().getName(), "otp-trigger")
                .forJob(jobDetail)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .startAt(Date.from(dateTime.toInstant()))
                .build();
    }
}
