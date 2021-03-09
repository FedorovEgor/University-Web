package com.fedorov.spring.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedorov.spring.service.StudentService;
import com.fedorov.spring.service.dto.GroupDTO;
import com.fedorov.spring.service.dto.StudentDTO;
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

@WebMvcTest(StudentsRestController.class)
public class StudentsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @Test
    public void selectAllStudents_allStudentsExpected() throws Exception {
        List<StudentDTO> expectedStudents = Arrays.asList(
                new StudentDTO(new GroupDTO(1, "group1"), "Paul", "Howard"),
                new StudentDTO(new GroupDTO(4, "group4"), "Thomas", "Reed")
        );

        Mockito.when(studentService.selectAllStudents()).thenReturn(expectedStudents);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedStudents);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(studentService).selectAllStudents();
    }

    @Test
    public void selectSingleStudent_singleStudentIsExpected() throws Exception {
        StudentDTO expectedStudent = new StudentDTO(new GroupDTO(1, "group1"), "Tod", "Howard");

        Mockito.when(studentService.selectStudent(1)).thenReturn(expectedStudent);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students/single/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedStudent);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(studentService).selectStudent(1);
    }

    @Test
    public void addStudent_studentExpectedToBeAdded() throws Exception {
        StudentDTO savedStudentDTO = new StudentDTO(new GroupDTO(1, "group1"), "Tod", "Howard");

        studentService.insertStudent(savedStudentDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/students/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(savedStudentDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateStudent_studentExpectedToBeUpdated() throws Exception {
        StudentDTO studentDTOThatWillBeUpdated = new StudentDTO(new GroupDTO(1, "group1"), "Tod", "Howard");

        mockMvc.perform(MockMvcRequestBuilders.put("/students/update/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(studentDTOThatWillBeUpdated)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteStudent_studentExpectedToBeDeleted() throws Exception {
        Mockito.when(studentService.deleteStudent(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/students/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

