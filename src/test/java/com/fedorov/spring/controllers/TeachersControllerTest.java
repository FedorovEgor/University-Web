package com.fedorov.spring.controllers;

import com.fedorov.spring.service.TeacherService;
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
public class TeachersControllerTest {

    private MockMvc mockMvc;

    @Mock
    TeacherService teacherService;

    @InjectMocks
    TeachersController teachersController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(teachersController).build();
    }

    @Test
    public void selectAllTeachers_allTeachersPageShown() throws Exception {
        List<TeacherDTO> expectedTeachers = Arrays.asList(new TeacherDTO("Bob", "Cop"));

        when(teacherService.selectAllTeachers()).thenReturn(expectedTeachers);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/teachers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("teachers/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("teachers"))
                .andExpect(MockMvcResultMatchers.model().attribute("teachers", expectedTeachers));

        verify(teacherService).selectAllTeachers();
    }

    @Test
    public void getTeacherById_singleTeacherPageShown() throws Exception {
        TeacherDTO expectedTeacher = new TeacherDTO(1,"Morgan", "Murray");

        when(teacherService.selectTeacher(1)).thenReturn(expectedTeacher);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/teachers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("teachers/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("teacher"))
                .andExpect(MockMvcResultMatchers.model().attribute("teacher", expectedTeacher));

        verify(teacherService).selectTeacher(1);
    }

    @Test
    public void newTeacher_newTeacherPageShown() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/teachers/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("teachers/new"));
    }

    @Test
    public void createTeacher_newTeacherCreatedAndPageRedirected() throws Exception {
        TeacherDTO expectedTeacher = new TeacherDTO(1,"Morgan", "Murray");

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/teachers")
                    .param("teacherId", "1")
                    .param("firstName", "Morgan")
                    .param("lastName", "Murray"))
                .andExpect(redirectedUrl("/teachers"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/teachers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("teacher"))
                .andExpect(MockMvcResultMatchers.model().attribute("teacher", expectedTeacher));
    }

    @Test
    public void editTeacher_editPageShown() throws Exception {
        TeacherDTO expectedTeacher = new TeacherDTO(1,"Morgan", "Murray");

        when(teacherService.selectTeacher(1)).thenReturn(expectedTeacher);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/teachers/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("teachers/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("teacher"))
                .andExpect(MockMvcResultMatchers.model().attribute("teacher", expectedTeacher));

        verify(teacherService).selectTeacher(1);
    }

    @Test
    public void updateTeacher_updateTeacherAndPageRedirected() throws Exception {
        TeacherDTO expectedTeacher = new TeacherDTO(1,"Morgan", "Murray");
        boolean isUpdated;
        when(teacherService.updateTeacher(expectedTeacher)).thenReturn(isUpdated = true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.patch("/teachers/1")
                        .param("teacherId", "1")
                        .param("firstName", "Morgan")
                        .param("lastName", "Murray"))
                .andExpect(redirectedUrl("/teachers"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/teachers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("teacher"))
                .andExpect(MockMvcResultMatchers.model().attribute("teacher", expectedTeacher));

        assertTrue(isUpdated);
        verify(teacherService).updateTeacher(expectedTeacher);
    }

    @Test
    public void deleteTeacher_teacherDeletedAndPageRedirected() throws Exception {
        boolean isDeleted;
        when(teacherService.deleteTeacher(1)).thenReturn(isDeleted = true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/teachers/1"))
                .andExpect(redirectedUrl("/teachers"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/teachers"));

        assertTrue(isDeleted);
        verify(teacherService).deleteTeacher(1);
    }
}
