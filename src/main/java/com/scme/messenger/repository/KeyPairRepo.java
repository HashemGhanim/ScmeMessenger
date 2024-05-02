package com.scme.messenger.repository;

import com.scme.messenger.model.KeyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyPairRepo extends JpaRepository<KeyPair , Integer> {
}
