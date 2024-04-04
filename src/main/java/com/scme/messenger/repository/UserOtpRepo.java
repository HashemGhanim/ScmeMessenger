package com.scme.messenger.repository;

import com.scme.messenger.model.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOtpRepo extends JpaRepository<UserOtp , String> {
}
