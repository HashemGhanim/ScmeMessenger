package com.scme.messenger.repository;

import com.scme.messenger.model.Course;
import com.scme.messenger.model.composite.CourseID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CourseRepo extends JpaRepository<Course , CourseID> {

    @Query("SELECT c FROM Course AS c INNER JOIN c.users AS cu WHERE cu.userId = ?1")
    Set<Course> findAllByStudentId(String studentId);

    @Query("SELECT CASE WHEN COUNT(cu) > 0 THEN true ELSE false END FROM Course AS c INNER JOIN c.users AS cu WHERE c.courseID.moduleId =?1 AND cu.userId=?2")
    boolean isStudentSubscribed(String moduleId, String studentId);
}
