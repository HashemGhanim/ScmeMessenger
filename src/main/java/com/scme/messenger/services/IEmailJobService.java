package com.scme.messenger.services;

import java.time.LocalDateTime;

import org.quartz.SchedulerException;

public interface IEmailJobService {

    void scheduleJobs(LocalDateTime localDateTime, String userId) throws SchedulerException;
}
