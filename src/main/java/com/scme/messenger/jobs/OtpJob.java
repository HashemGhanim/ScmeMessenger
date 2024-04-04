package com.scme.messenger.jobs;

import com.scme.messenger.model.UserOtp;
import com.scme.messenger.repository.UserOtpRepo;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class OtpJob extends QuartzJobBean {

    private final UserOtpRepo userOtpRepo;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getMergedJobDataMap();

        String userId = (String) map.get("userId");
        String userOtp = (String) map.get("userOtp");

        deleteOtpFromUserOtpTable(userId , userOtp);
    }


    private void deleteOtpFromUserOtpTable(String userId , String otp){
        UserOtp userOtp = UserOtp.builder()
                .userId(userId)
                .otp(otp)
                .build();
        userOtpRepo.delete(userOtp);
    }
}
