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
                .name("Hashem")
                .userId("20201101")
                .password(passwordEncoder.encode("Hh@123456"))
                .role(Role.STUDENT)
                .registered(true)
                .build();

        User user2 = User.builder()
                .email("moodghoz@gmail.com")
                .name("Mahmmoud")
                .userId("20201102")
                .password(passwordEncoder.encode("Hh@123456"))
                .role(Role.ADMIN)
                .registered(true)
                .build();

        User user3 = User.builder()
                .email("salmanzerei45@gmail.com")
                .name("Salman")
                .userId("20201103")
                .password(passwordEncoder.encode("Hh@123456"))
                .role(Role.STUDENT)
                .registered(true)
                .build();

        User user4 = User.builder()
                .email("hasheeem1712@gmail.com")
                .name("Sami")
                .userId("20201104")
                .password(passwordEncoder.encode("Hh@123456"))
                .role(Role.STUDENT)
                .registered(true)
                .build();

        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);
        userRepo.save(user4);
    }

}
