package com.scme.messenger;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class ScmeMessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScmeMessengerApplication.class, args);
	}
}
