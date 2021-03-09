package com.fedorov.spring.repositoryService;

import com.fedorov.spring.model.StudentCourse;
import com.fedorov.spring.repository.StudentCourseRepository;
import com.fedorov.spring.service.StudentCourseService;
import com.fedorov.spring.service.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentCourseRepositoryServiceTest {

    @Mock
    StudentCourseRepository studentCourseRepository;

    @InjectMocks
    private StudentCourseService studentCourseService;

    @Test
    void getAllStudentsCourses_listOfAllStudentsCoursesExpected() {
        List<StudentCourse> expectedStudentsCourses = Arrays.asList(
                new StudentCourse(1,3),
                new StudentCourse(2,5)
        );

        when(studentCourseRepository.findAll()).thenReturn(expectedStudentsCourses);
        List<StudentCourseDTO> actualStudentsCourses = studentCourseService.selectAllStudentsCourses();
        String actualStudentsCoursesElementsClass = actualStudentsCourses.get(0).getClass().getSimpleName();

        assertEquals("StudentCourseDTO", actualStudentsCoursesElementsClass);
        verify(studentCourseRepository).findAll();
    }

    @Test
    void getStudentCourseById_singleStudentCourseExpected() {
        StudentCourse expectedStudentCourse = new StudentCourse(1, 7);

        when(studentCourseRepository.getOne(1)).thenReturn(expectedStudentCourse);
        StudentCourseDTO actualStudentCourse = studentCourseService.selectStudentCourse(1);
        String actualStudentCourseClass = actualStudentCourse.getClass().getSimpleName();

        assertEquals("StudentCourseDTO", actualStudentCourseClass);
        verify(studentCourseRepository).getOne(1);
    }

    @Test
    void getStudentCourseByStudentIdAndCourseId_singleStudentCourseIsExpected() {
        StudentCourse expectedStudentCourse = new StudentCourse(5, 9);

        when(studentCourseRepository.findByStudentIdAndCourseId(5,9)).thenReturn(expectedStudentCourse);
        StudentCourseDTO actualStudentCourse = studentCourseService.selectStudentCourse(5,9);
        String actualStudentCourseClass = actualStudentCourse.getClass().getSimpleName();

        assertEquals("StudentCourseDTO", actualStudentCourseClass);
        verify(studentCourseRepository).findByStudentIdAndCourseId(5,9);
    }

    @Test
    void deleteStudentCourseById_studentCourseExpectedToBeDeleted() {
        boolean isDeleted;

        isDeleted = studentCourseService.deleteStudentCourse(1);

        assertTrue(isDeleted);
        verify(studentCourseRepository).deleteById(1);
    }

    @Test
    void updateStudentCourse_studentCourseExpectedToBeUpdated() {
        boolean isUpdated;

        StudentCourse studentCourse = new StudentCourse(7, 1);
        StudentCourseDTO studentCourseDTO = new StudentCourseDTO(7, 1);
        isUpdated = studentCourseService.updateStudentCourse(studentCourseDTO);

        assertTrue(isUpdated);
        verify(studentCourseRepository).save(studentCourse);
    }

    @Test
    void insertStudentCourse_newStudentCourseExpectedToBeCreatedInDataBase() {
        StudentCourse studentCourse = new StudentCourse(4, 4);
        StudentCourseDTO studentCourseDTO = new StudentCourseDTO(4, 4);
        studentCourseService.insertStudentCourse(studentCourseDTO);
        verify(studentCourseRepository).save(studentCourse);
    }

    @Test
    void addStudentToCourse_studentShouldBeAddedToSpecificCourse() {
        StudentDTO studentDTO = new StudentDTO(1, new GroupDTO(6, "hg-77"),"Bob", "Coul");
        CourseDTO courseDTO = new CourseDTO(1, new TeacherDTO(1, "Sam", "Fisher"), "Java");
        StudentCourse studentCourse = new StudentCourse(studentDTO.getStudentId(),courseDTO.getCourseId());
        studentCourseService.addStudentToCourse(studentDTO, courseDTO);

        verify(studentCourseRepository).save(studentCourse);
    }

    @Test
    void deleteStudentFromCourse_studentShouldBeDeletedFromSpecificCourse() {
        StudentDTO studentDTO = new StudentDTO(4, new GroupDTO(3, "ff-51"), "Jim", "Moor");
        CourseDTO courseDTO = new CourseDTO(6, new TeacherDTO(4, "Mike", "Riggs"),"Maths");
        StudentCourse studentCourse = new StudentCourse(studentDTO.getStudentId(), courseDTO.getCourseId());


        when(studentCourseRepository.findByStudentIdAndCourseId(studentDTO.getStudentId(), courseDTO.getCourseId())).thenReturn(studentCourse);
        studentCourseService.deleteStudentFromCourse(studentDTO,courseDTO);
        verify(studentCourseRepository).findByStudentIdAndCourseId(studentDTO.getStudentId(), courseDTO.getCourseId());
        verify(studentCourseRepository).deleteById(studentCourse.getId());
    }
}

