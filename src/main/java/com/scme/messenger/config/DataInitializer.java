package com.scme.messenger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.scme.messenger.constants.Role;
import com.scme.messenger.model.User;
import com.scme.messenger.repository.UserRepo;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user1 = User.builder()
                .email("hashemzerei45@gmail.com")
                .name("hashem")
                .userId("20201101")
                .role(Role.STUDENT)
                .build();

        User user2 = User.builder()
                .email("moodghoz@gmail.com")
                .name("Mahmmod")
                .userId("20201102")
                .password(passwordEncoder.encode("Hh@123456"))
                .role(Role.STUDENT)
                .build();

        userRepo.save(user1);
        userRepo.save(user2);
    }

}
