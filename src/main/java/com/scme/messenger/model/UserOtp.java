package com.scme.messenger.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user-otp")
public class UserOtp extends BaseEntity{

    @Id
    private String userId;
    private String otp;
}
