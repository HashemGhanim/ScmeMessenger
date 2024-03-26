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
        User user = User.builder()
                .email("hashemzerei45@gmail.com")
                .name("hashem")
                .password(passwordEncoder.encode("12345678"))
                .userId("202011016")
                .role(Role.STUDENT)
                .build();

        userRepo.save(user);
    }

}
