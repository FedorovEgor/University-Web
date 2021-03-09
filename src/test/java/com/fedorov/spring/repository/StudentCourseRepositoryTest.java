package com.fedorov.spring.repository;

import com.fedorov.spring.model.StudentCourse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class StudentCourseRepositoryTest {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Test
    void getStudentCourseByStudentIdAndCourseId_singleStudentCourseIsExpected() {
        StudentCourse expectedStudentCourse = new StudentCourse(1, 2, 1);
        StudentCourse actualStudentCourse = studentCourseRepository.findByStudentIdAndCourseId(2, 1);

        assertEquals(expectedStudentCourse, actualStudentCourse);
    }
}
