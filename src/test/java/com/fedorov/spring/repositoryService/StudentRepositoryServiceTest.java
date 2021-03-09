package com.fedorov.spring.repositoryService;

import com.fedorov.spring.model.Group;
import com.fedorov.spring.model.Student;
import com.fedorov.spring.repository.StudentRepository;
import com.fedorov.spring.service.StudentService;
import com.fedorov.spring.service.dto.GroupDTO;
import com.fedorov.spring.service.dto.StudentDTO;
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
public class StudentRepositoryServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void getAllStudents_listOfAllStudentsExpected() {
        List<Student> expectedStudents = Arrays.asList(
                new Student(new Group(1, "group1"), "Paul", "Howard"),
                new Student(new Group(4, "group4"), "Thomas", "Reed")
        );

        when(studentRepository.findAll()).thenReturn(expectedStudents);
        List<StudentDTO> actualStudents = studentService.selectAllStudents();
        String actualStudentsElementsClass = actualStudents.get(0).getClass().getSimpleName();

        assertEquals("StudentDTO", actualStudentsElementsClass);
        verify(studentRepository).findAll();
    }

    @Test
    void getStudentById_singleStudentIsExpected() {
        Student expectedStudent = new Student(6, new Group(4, "group4"), "Bill", "Murray");

        when(studentRepository.getOne(6)).thenReturn(expectedStudent);
        StudentDTO actualStudent = studentService.selectStudent(6);
        String actualStudentClass = actualStudent.getClass().getSimpleName();

        assertEquals("StudentDTO", actualStudentClass);
        verify(studentRepository).getOne(6);
    }

    @Test
    void deleteStudentById_studentExpectedToBeDeleted() {
        boolean isDeleted;

        isDeleted = studentService.deleteStudent(1);

        assertTrue(isDeleted);
        verify(studentRepository).deleteById(1);
    }

    @Test
    void updateStudent_studentExpectedToBeUpdated() {
        boolean isUpdated;

        Student student = new Student(1, new Group(5, "fk-78"), "Thomas", "Left");
        StudentDTO studentDTO = new StudentDTO(1, new GroupDTO(5, "fk-78"), "Thomas", "Left");
        isUpdated = studentService.updateStudent(studentDTO);

        assertTrue(isUpdated);
        verify(studentRepository).save(student);
    }

    @Test
    void insertStudent_newStudentExpectedToBeCreatedInDataBase() {
        Student student = new Student(1, new Group(2, "fk-78"), "Betty", "Walker");
        StudentDTO studentDTO = new StudentDTO(1, new GroupDTO(2, "fk-78"), "Betty", "Walker");
        studentService.insertStudent(studentDTO);
        verify(studentRepository).save(student);
    }
}

