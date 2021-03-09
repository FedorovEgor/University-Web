package com.fedorov.spring.repositoryService;

import com.fedorov.spring.model.Course;
import com.fedorov.spring.model.Teacher;
import com.fedorov.spring.repository.CourseRepository;
import com.fedorov.spring.service.CourseService;
import com.fedorov.spring.service.dto.CourseDTO;
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
public class CourseRepositoryServiceTest {

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void getAllCourses_listOfAllCoursesExpected() {
        List<Course> expectedCourses = Arrays.asList(
                new Course(1, new Teacher(2, "", ""),"Java"),
                new Course(2, new Teacher(1, "", ""), "Python")
        );

        when(courseRepository.findAll()).thenReturn(expectedCourses);
        List<CourseDTO> actualCourses = courseService.selectAllCourses();
        String actualCoursesElementClass = actualCourses.get(0).getClass().getSimpleName();

        assertEquals("CourseDTO", actualCoursesElementClass);
        verify(courseRepository).findAll();
    }

    @Test
    void getCourseById_singleCourseIsExpected() {
        Course expectedCourse = new Course(5,new Teacher(1, "teacher", "one"),"Maths");
        when(courseRepository.getOne(5)).thenReturn(expectedCourse);
        CourseDTO actualCourse = courseService.selectCourse(5);
        String actualCourseClass = actualCourse.getClass().getSimpleName();

        assertEquals("CourseDTO", actualCourseClass);
        verify(courseRepository).getOne(5);
    }

    @Test
    void deleteCourseById_courseExpectedToBeDeleted() {
        boolean isDeleted;

        isDeleted = courseService.deleteCourse(4);

        assertTrue(isDeleted);
        verify(courseRepository).deleteById(4);
    }

    @Test
    void updateCourse_courseExpectedToBeUpdated() {
        boolean isUpdated;

        Course course = new Course(new Teacher(5, "Morgan", "Fox"), "Biology");
        CourseDTO courseDTO = new CourseDTO(new TeacherDTO(5, "Morgan", "Fox"), "Biology");
        isUpdated = courseService.updateCourse(courseDTO);

        assertTrue(isUpdated);
        verify(courseRepository).save(course);
    }

    @Test
    void insertCourse_newCourseExpectedToBeCreated() {
        Course course = new Course(1, new Teacher(4, "Josh", "Baley"), "Java");
        CourseDTO courseDTO = new CourseDTO(1, new TeacherDTO(4, "Josh", "Baley"), "Java");
        courseService.insertCourse(courseDTO);
        verify(courseRepository).save(course);
    }

}
