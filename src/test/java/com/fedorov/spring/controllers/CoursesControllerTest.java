package com.fedorov.spring.controllers;

import com.fedorov.spring.service.CourseService;
import com.fedorov.spring.service.TeacherService;
import com.fedorov.spring.service.dto.CourseDTO;
import com.fedorov.spring.service.dto.TeacherDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
public class CoursesControllerTest {

    private MockMvc mockMvc;

    @Mock
    CourseService courseService;
    @Mock
    TeacherService teacherService;

    @InjectMocks
    CoursesController coursesController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(coursesController).build();
    }

    @Test
    public void selectAllCourses_allCoursesPageShown() throws Exception {
        List<CourseDTO> expectedCourses = Arrays.asList(new CourseDTO(1, "Java"));

        when(courseService.selectAllCourses()).thenReturn(expectedCourses);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/courses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("courses/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("courses"))
                .andExpect(MockMvcResultMatchers.model().attribute("courses", expectedCourses));

        verify(courseService).selectAllCourses();
    }

    @Test
    public void singleCourseSelected_singleCoursePageShown() throws Exception {
        CourseDTO expectedCourse = new CourseDTO(1, "Python");

        when(courseService.selectCourse(1)).thenReturn(expectedCourse);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/courses/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("courses/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.model().attribute("course", expectedCourse));

        verify(courseService).selectCourse(1);
    }

    @Test
    public void newCourse_newCoursePageShown() throws Exception {
        List<TeacherDTO> expectedTeachers = Arrays.asList(new TeacherDTO("Morgan", "Murray"));

        when(teacherService.selectAllTeachers()).thenReturn(expectedTeachers);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/courses/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("courses/new"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("teachers"))
                .andExpect(MockMvcResultMatchers.model().attribute("teachers", expectedTeachers));

        verify(teacherService).selectAllTeachers();
    }

    @Test
    public void createNewCourse_newCourseCreatedAndPageRedirected() throws Exception {
        CourseDTO expectedCourse = new CourseDTO("Java");

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/courses")
                        .param("courseName", "Java"))
                .andExpect(redirectedUrl("/courses"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.model().attribute("course", expectedCourse));

    }

    @Test
    public void editCourse_editPageShown() throws Exception {
        CourseDTO expectedCourse = new CourseDTO(1, "Java");
        List<TeacherDTO> expectedTeachers = Arrays.asList(new TeacherDTO("Morgan", "Murray"));

        when(courseService.selectCourse(1)).thenReturn(expectedCourse);
        when(teacherService.selectAllTeachers()).thenReturn(expectedTeachers);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/courses/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("courses/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("teachers"))
                .andExpect(MockMvcResultMatchers.model().attribute("course", expectedCourse))
                .andExpect(MockMvcResultMatchers.model().attribute("teachers", expectedTeachers));

        verify(courseService).selectCourse(1);
        verify(teacherService).selectAllTeachers();
    }

    @Test
    public void updateCourse_CourseUpdatedAndPageRedirected() throws Exception {
        CourseDTO expectedCourse = new CourseDTO(1, "Java");
        boolean isUpdated;
        when(courseService.updateCourse(expectedCourse)).thenReturn(isUpdated = true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.patch("/courses/1")
                        .param("courseId", "1")
                        .param("courseName", "Java"))
                .andExpect(redirectedUrl("/courses"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.model().attribute("course", expectedCourse));

        assertTrue(isUpdated);
        verify(courseService).updateCourse(expectedCourse);
    }

    @Test
    public void deleteCourse_courseDeletedAndPageRedirected() throws Exception {
        boolean isDeleted;
        when(courseService.deleteCourse(1)).thenReturn(isDeleted = true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/courses/1"))
                .andExpect(redirectedUrl("/courses"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/courses"));

        assertTrue(isDeleted);
        verify(courseService).deleteCourse(1);
    }
}
