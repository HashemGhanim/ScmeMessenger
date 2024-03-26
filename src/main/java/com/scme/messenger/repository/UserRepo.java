package com.scme.messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scme.messenger.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
}
