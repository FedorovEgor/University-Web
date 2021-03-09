package com.fedorov.spring.repository;

import com.fedorov.spring.model.StudentCourse;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Profile("default")
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {

    @Query("select sc from StudentCourse sc where sc.student_id = ?1 and sc.course_id = ?2")
    StudentCourse findByStudentIdAndCourseId(int studentId, int courseId);
}
