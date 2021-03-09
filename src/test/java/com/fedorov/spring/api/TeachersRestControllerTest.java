package com.fedorov.spring.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedorov.spring.service.TeacherService;
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

@WebMvcTest(TeachersRestController.class)
public class TeachersRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TeacherService teacherService;

    @Test
    public void selectAllTeachers_allTeachersExpected() throws Exception {
        List<TeacherDTO> expectedTeachers = Arrays.asList(
                new TeacherDTO(1, "Paul", "Howard"),
                new TeacherDTO(4, "Thomas", "Reed")
        );

        Mockito.when(teacherService.selectAllTeachers()).thenReturn(expectedTeachers);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/teachers/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedTeachers);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(teacherService).selectAllTeachers();
    }

    @Test
    public void getSingleTeacher_singleTeacherIsExpected() throws Exception {
        TeacherDTO expectedTeacher = new TeacherDTO(1, "Paul", "Howard");

        Mockito.when(teacherService.selectTeacher(1)).thenReturn(expectedTeacher);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/teachers/single/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedTeacher);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(teacherService).selectTeacher(1);
    }

    @Test
    public void addTeacher_newTeacherExpectedToBeAdded() throws Exception {
        TeacherDTO savedTeacherDTO = new TeacherDTO(3, "Tod", "Howard");

        teacherService.insertTeacher(savedTeacherDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/teachers/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(savedTeacherDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateTeacher_teacherExpectedToBeUpdated() throws Exception {
        TeacherDTO teacherDTOThatWillBeUpdated = new TeacherDTO(1, "Paul", "Howard");

        mockMvc.perform(MockMvcRequestBuilders.put("/teachers/update/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(teacherDTOThatWillBeUpdated)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteTeacher_teacherExpectedToBeDeleted() throws Exception {
        Mockito.when(teacherService.deleteTeacher(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/teachers/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
