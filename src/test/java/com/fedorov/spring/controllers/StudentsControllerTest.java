package com.fedorov.spring.controllers;

import com.fedorov.spring.service.GroupService;
import com.fedorov.spring.service.StudentService;
import com.fedorov.spring.service.dto.GroupDTO;
import com.fedorov.spring.service.dto.StudentDTO;
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
public class StudentsControllerTest {

    private MockMvc mockMvc;

    @Mock
    StudentService studentService;
    @Mock
    GroupService groupService;

    @InjectMocks
    StudentsController studentsController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentsController).build();
    }

    @Test
    public void selectAllStudents_allStudentsPageShown() throws Exception {
        List<StudentDTO> expectedStudents = Arrays.asList(new StudentDTO(new GroupDTO(),"Egor","Fedorov"));

        when(studentService.selectAllStudents()).thenReturn(expectedStudents);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("students/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("students"))
                .andExpect(MockMvcResultMatchers.model().attribute("students", expectedStudents));

        verify(studentService).selectAllStudents();
    }

    @Test
    public void getStudentById_singleStudentPageShown() throws Exception {
        StudentDTO expectedStudent = new StudentDTO(1, new GroupDTO(),"Egor","Fedorov");

        when(studentService.selectStudent(1)).thenReturn(expectedStudent);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/students/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("students/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("student"))
                .andExpect(MockMvcResultMatchers.model().attribute("student", expectedStudent));

        verify(studentService).selectStudent(1);
    }

    @Test
    public void newStudent_newStudentPageShown() throws Exception {
        List<GroupDTO> expectedGroups = Arrays.asList(new GroupDTO("fk-44"));

        when(groupService.selectAllGroups()).thenReturn(expectedGroups);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/students/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("students/new"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("groups"))
                .andExpect(MockMvcResultMatchers.model().attribute("groups", expectedGroups));

        verify(groupService).selectAllGroups();
    }


    @Test
    public void createNewStudent_newStudentCreatedAndPageRedirected() throws Exception {
        StudentDTO expectedStudent = new StudentDTO("Egor", "Fedorov");

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/students")
                        .param("firstName", "Egor").param("lastName", "Fedorov"))
                .andExpect(redirectedUrl("/students"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("student"))
                .andExpect(MockMvcResultMatchers.model().attribute("student", expectedStudent));
    }

    @Test
    public void editStudent_editPageShown() throws Exception {
        StudentDTO expectedStudent = new StudentDTO(1, "Egor", "Fedorov");
        List<GroupDTO> expectedGroups = Arrays.asList(new GroupDTO("fk-44"));

        when(studentService.selectStudent(1)).thenReturn(expectedStudent);
        when(groupService.selectAllGroups()).thenReturn(expectedGroups);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/students/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("students/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("student"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("groups"))
                .andExpect(MockMvcResultMatchers.model().attribute("student", expectedStudent))
                .andExpect(MockMvcResultMatchers.model().attribute("groups", expectedGroups));

        verify(studentService).selectStudent(1);
        verify(groupService).selectAllGroups();
    }

    @Test
    public void updateStudent_studentUpdatedAndPageRedirected() throws Exception {
        StudentDTO expectedStudent = new StudentDTO(1,"Egor", "Fedorov");
        boolean isUpdated;
        when(studentService.updateStudent(expectedStudent)).thenReturn(isUpdated = true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.patch("/students/1")
                        .param("studentId", "1")
                        .param("firstName", "Egor")
                        .param("lastName", "Fedorov"))
                .andExpect(redirectedUrl("/students"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("student"))
                .andExpect(MockMvcResultMatchers.model().attribute("student", expectedStudent));

        assertTrue(isUpdated);
        verify(studentService).updateStudent(expectedStudent);
    }

    @Test
    public void deleteStudent_studentDeletedAndPageRedirected() throws Exception {
        boolean isDeleted;
        when(studentService.deleteStudent(1)).thenReturn(isDeleted = true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/students/1"))
                .andExpect(redirectedUrl("/students"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/students"));

        assertTrue(isDeleted);
        verify(studentService).deleteStudent(1);
    }
}
