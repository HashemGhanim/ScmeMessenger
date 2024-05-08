package com.scme.messenger.config;

import com.scme.messenger.model.KeyPair;
import com.scme.messenger.services.IKeyPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.scme.messenger.constants.Role;
import com.scme.messenger.model.User;
import com.scme.messenger.repository.UserRepo;

//@Profile("!test")
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IKeyPairService iKeyPairService;

    @Override
    public void run(String... args) throws Exception {
        User user1 = User.builder()
                .email("hashemzerei45@gmail.com")
                .name("Hashem")
                .userId("20201101")
                .password(passwordEncoder.encode("Hh@123456"))
                .role(Role.DOCTOR)
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

        KeyPair keyPair1 = iKeyPairService.save(user1);
        KeyPair keyPair2 = iKeyPairService.save(user2);
        KeyPair keyPair3 = iKeyPairService.save(user3);
        KeyPair keyPair4 = iKeyPairService.save(user4);

        user1.setKeyPair(keyPair1);
        user2.setKeyPair(keyPair2);
        user3.setKeyPair(keyPair3);
        user4.setKeyPair(keyPair4);

        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);
        userRepo.save(user4);
    }

}
