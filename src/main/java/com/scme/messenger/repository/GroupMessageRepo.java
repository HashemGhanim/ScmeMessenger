package com.scme.messenger.repository;

import com.scme.messenger.model.GroupMessage;
import com.scme.messenger.model.composite.GroupMessageID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMessageRepo extends JpaRepository<GroupMessage , GroupMessageID> {
}
