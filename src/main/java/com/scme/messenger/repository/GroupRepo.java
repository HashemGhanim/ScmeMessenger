package com.scme.messenger.repository;

import com.scme.messenger.model.Course;
import com.scme.messenger.model.composite.CourseID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends JpaRepository<Course , CourseID> {
}
