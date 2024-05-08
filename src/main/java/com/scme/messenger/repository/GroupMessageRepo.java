package com.scme.messenger.repository;

import com.scme.messenger.model.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupMessageRepo extends JpaRepository<GroupMessage , UUID> {
}
