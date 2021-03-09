package com.fedorov.spring.repository;

import com.fedorov.spring.model.Course;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("default")
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
