package com.scme.messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scme.messenger.model.User;

import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.userId = ?1")
    boolean isUserExist(String userId);

    @Query("SELECT u FROM User u WHERE u.name LIKE CONCAT('%', :username ,'%') AND u.registered = true")
    Set<User> findUserByPartOfName(@Param("username") String username);
}
