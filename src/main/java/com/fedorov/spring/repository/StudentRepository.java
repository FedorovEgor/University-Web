package com.fedorov.spring.repository;

import com.fedorov.spring.model.Student;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("default")
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
