package com.fedorov.spring.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedorov.spring.service.CourseService;
import com.fedorov.spring.service.dto.CourseDTO;
import com.fedorov.spring.service.dto.TeacherDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(CoursesRestController.class)
public class CoursesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService courseService;

    @Test
    public void selectAllCourses_allCoursesExpected() throws Exception {
        List<CourseDTO> expectedCourses = Arrays.asList(
                new CourseDTO(1, new TeacherDTO(2, "", ""),"Java"),
                new CourseDTO(2, new TeacherDTO(1, "", ""), "Python")
        );

        Mockito.when(courseService.selectAllCourses()).thenReturn(expectedCourses);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/courses/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedCourses);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(courseService).selectAllCourses();
    }

    @Test
    public void selectSingleCourse_singleCourseIsExpected() throws Exception {
        CourseDTO expectedCourse = new CourseDTO(1, new TeacherDTO(2, "", ""),"Java");

        Mockito.when(courseService.selectCourse(1)).thenReturn(expectedCourse);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/courses/single/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedCourse);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(courseService).selectCourse(1);
    }

    @Test
    public void addCourse_newCourseExpectedToBeAdded() throws Exception {
        CourseDTO savedCourseDTO = new CourseDTO(1, new TeacherDTO(2, "", ""),"Java");

        courseService.insertCourse(savedCourseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/courses/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(savedCourseDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateCourse_courseExpectedToBeUpdated() throws Exception {
        CourseDTO courseDTOThatWillBeUpdated = new CourseDTO(1, new TeacherDTO(2, "", ""), "Java");

        mockMvc.perform(MockMvcRequestBuilders.put("/courses/update/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(courseDTOThatWillBeUpdated)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteCourse_courseExpectedToBeDeleted() throws Exception {
        Mockito.when(courseService.deleteCourse(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/courses/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
