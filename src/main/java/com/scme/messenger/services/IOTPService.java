package com.scme.messenger.services;

import org.quartz.SchedulerException;

public interface IOTPService {
    String generateOTP(String userId) throws SchedulerException;
}
