package com.scme.messenger.jobs;

import java.nio.charset.StandardCharsets;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.scme.messenger.constants.MailContent;
import com.scme.messenger.services.IOTPService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class EmailJob extends QuartzJobBean {

    private final MailProperties mailProperties;

    private final JavaMailSender sender;

    private final IOTPService otpService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jobDataMap = context.getMergedJobDataMap();

        String email = (String) jobDataMap.get("userEmail");
        String username = (String) jobDataMap.get("userName");
        String userId = (String) jobDataMap.get("userId");

        try {
            sendEmail(mailProperties.getUsername().toString(), email, username, MailContent.subject , userId);
        } catch (MessagingException | SchedulerException e) {
            e.printStackTrace();
        }

    }

    private void sendEmail(String from, String to, String name, String subject, String userId) throws MessagingException, SchedulerException {
        MimeMessage mimeMessage = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.toString());

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(MailContent.MAIL_CONTENT(name, otpService.generateOTP(userId)), true);

        sender.send(mimeMessage);
    }

}
