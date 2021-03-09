package com.fedorov.spring.repositoryService;

import com.fedorov.spring.model.Teacher;
import com.fedorov.spring.repository.TeacherRepository;
import com.fedorov.spring.service.TeacherService;
import com.fedorov.spring.service.dto.TeacherDTO;
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
public class TeacherRepositoryServiceTest {

    @Mock
    TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void getAllTeachers_listOfAllTeacherExpected() {
        List<Teacher> expectedTeachers = Arrays.asList(
                new Teacher(1, "Paul", "Howard"),
                new Teacher(4, "Thomas", "Reed")
        );

        when(teacherRepository.findAll()).thenReturn(expectedTeachers);
        List<TeacherDTO> actualTeachers = teacherService.selectAllTeachers();
        String actualTeachersElementsClass = actualTeachers.get(0).getClass().getSimpleName();

        assertEquals("TeacherDTO", actualTeachersElementsClass);
        verify(teacherRepository).findAll();
    }

    @Test
    void getTeacherById_singleTeacherIsExpected() {
        Teacher expectedTeacher = new Teacher(7, "Josh", "Smith");

        when(teacherRepository.getOne(7)).thenReturn(expectedTeacher);
        TeacherDTO actualTeacher = teacherService.selectTeacher(7);
        String actualTeacherClass = actualTeacher.getClass().getSimpleName();

        assertEquals("TeacherDTO", actualTeacherClass);
        verify(teacherRepository).getOne(7);
    }

    @Test
    void deleteTeacherById_teacherExpectedToBeDeleted() {
        boolean isDeleted;

        isDeleted = teacherService.deleteTeacher(6);

        assertTrue(isDeleted);
        verify(teacherRepository).deleteById(6);
    }

    @Test
    void updateTeacher_teacherExpectedToBeUpdated() {
        boolean isUpdated;

        Teacher teacher = new Teacher(5, "Mike", "Daniels");
        TeacherDTO teacherDTO = new TeacherDTO(5, "Mike", "Daniels");
        isUpdated = teacherService.updateTeacher(teacherDTO);

        assertTrue(isUpdated);
        verify(teacherRepository).save(teacher);
    }

    @Test
    void insertTeacher_teacherExpectedToBeCreatedInDataBase() {
        Teacher teacher = new Teacher(5, "Mike", "Daniels");
        TeacherDTO teacherDTO = new TeacherDTO(5, "Mike", "Daniels");
        teacherService.insertTeacher(teacherDTO);
        verify(teacherRepository).save(teacher);
    }
}

