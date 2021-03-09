package com.fedorov.spring.controllers;

import com.fedorov.spring.service.*;
import com.fedorov.spring.service.dto.*;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
public class SchedulesControllerTest {

    private MockMvc mockMvc;

    @Mock
    ScheduleService scheduleService;
    @Mock
    GroupService groupService;
    @Mock
    CourseService courseService;
    @Mock
    TeacherService teacherService;
    @Mock
    TimeTableService timeTableService;

    @InjectMocks
    SchedulesController schedulesController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(schedulesController).build();
    }

    @Test
    public void selectAllSchedules_allSchedulesPageShown() throws Exception {
        String stringDate = "2020-12-18";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        List<ScheduleDTO> expectedSchedules = Arrays.asList(new ScheduleDTO(1,
                new GroupDTO(), new CourseDTO(), new TeacherDTO(), new TimeTableDTO(), date));

        when(scheduleService.selectAllSchedules()).thenReturn(expectedSchedules);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/schedules"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("schedules/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("schedules"))
                .andExpect(MockMvcResultMatchers.model().attribute("schedules", expectedSchedules));

        verify(scheduleService).selectAllSchedules();
    }

    @Test
    public void selectScheduleById_singleSchedulePageShown() throws Exception {
        String stringDate = "2020-12-18";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        ScheduleDTO expectedSchedule = new ScheduleDTO(1,
                new GroupDTO(), new CourseDTO(), new TeacherDTO(), new TimeTableDTO(), date);

        when(scheduleService.selectSchedule(1)).thenReturn(expectedSchedule);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/schedules/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("schedules/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("schedule"))
                .andExpect(MockMvcResultMatchers.model().attribute("schedule", expectedSchedule));

        verify(scheduleService).selectSchedule(1);
    }

    @Test
    public void newSchedule_newSchedulePageShown() throws Exception {
        List<GroupDTO> expectedGroups = Arrays.asList(new GroupDTO());
        List<CourseDTO> expectedCourses = Arrays.asList(new CourseDTO());
        List<TeacherDTO> expectedTeachers = Arrays.asList(new TeacherDTO());
        List<TimeTableDTO> expectedTimeTables = Arrays.asList(new TimeTableDTO());

        when(groupService.selectAllGroups()).thenReturn(expectedGroups);
        when(courseService.selectAllCourses()).thenReturn(expectedCourses);
        when(teacherService.selectAllTeachers()).thenReturn(expectedTeachers);
        when(timeTableService.selectAllTimeTables()).thenReturn(expectedTimeTables);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/schedules/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("schedules/new"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("teachers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("timetables"))
                .andExpect(MockMvcResultMatchers.model().attribute("groups", expectedGroups))
                .andExpect(MockMvcResultMatchers.model().attribute("courses", expectedCourses))
                .andExpect(MockMvcResultMatchers.model().attribute("teachers", expectedTeachers))
                .andExpect(MockMvcResultMatchers.model().attribute("timetables", expectedTimeTables));

        verify(groupService).selectAllGroups();
        verify(courseService).selectAllCourses();
        verify(teacherService).selectAllTeachers();
        verify(timeTableService).selectAllTimeTables();
    }

    @Test
    public void createNewSchedule_newScheduleCreatedAndPageRedirected() throws Exception {
        String stringDate = "2020-12-18";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        ScheduleDTO expectedSchedule = new ScheduleDTO(1, date);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/schedules")
                        .param("scheduleId", "1")
                        .param("date", "2020-12-18"))
                .andExpect(redirectedUrl("/schedules"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/schedules"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("schedule"))
                .andExpect(MockMvcResultMatchers.model().attribute("schedule", expectedSchedule));
    }

    @Test
    public void editSchedule_editPageShown() throws Exception {
        String stringDate = "2020-12-18";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        ScheduleDTO expectedSchedule = new ScheduleDTO(new GroupDTO(), new CourseDTO(), new TeacherDTO(), new TimeTableDTO(), date);
        List<GroupDTO> expectedGroups = Arrays.asList(new GroupDTO());
        List<CourseDTO> expectedCourses = Arrays.asList(new CourseDTO());
        List<TeacherDTO> expectedTeachers = Arrays.asList(new TeacherDTO());
        List<TimeTableDTO> expectedTimeTables = Arrays.asList(new TimeTableDTO());

        when(scheduleService.selectSchedule(1)).thenReturn(expectedSchedule);
        when(groupService.selectAllGroups()).thenReturn(expectedGroups);
        when(courseService.selectAllCourses()).thenReturn(expectedCourses);
        when(teacherService.selectAllTeachers()).thenReturn(expectedTeachers);
        when(timeTableService.selectAllTimeTables()).thenReturn(expectedTimeTables);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/schedules/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("schedules/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("schedule"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("teachers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("timetables"))
                .andExpect(MockMvcResultMatchers.model().attribute("schedule", expectedSchedule))
                .andExpect(MockMvcResultMatchers.model().attribute("groups", expectedGroups))
                .andExpect(MockMvcResultMatchers.model().attribute("courses", expectedCourses))
                .andExpect(MockMvcResultMatchers.model().attribute("teachers", expectedTeachers))
                .andExpect(MockMvcResultMatchers.model().attribute("timetables", expectedTimeTables));

        verify(scheduleService).selectSchedule(1);
        verify(groupService).selectAllGroups();
        verify(courseService).selectAllCourses();
        verify(teacherService).selectAllTeachers();
        verify(timeTableService).selectAllTimeTables();
    }

    @Test
    public void updateSchedule_scheduleUpdatedAndPageRedirected() throws Exception {
        String stringDate = "2020-12-18";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        ScheduleDTO expectedSchedule = new ScheduleDTO(1, date);

        boolean isUpdated;
        when(scheduleService.updateSchedule(expectedSchedule)).thenReturn(isUpdated = true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.patch("/schedules/1")
                        .param("scheduleId", "1")
                        .param("date", "2020-12-18"))
                .andExpect(redirectedUrl("/schedules"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/schedules"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("schedule"))
                .andExpect(MockMvcResultMatchers.model().attribute("schedule", expectedSchedule));

        assertTrue(isUpdated);
        verify(scheduleService).updateSchedule(expectedSchedule);
    }

    @Test
    public void deleteSchedule_scheduleDeletedAndPageRedirected() throws Exception {
        boolean isDeleted;
        when(scheduleService.deleteSchedule(4)).thenReturn(isDeleted = true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/schedules/4"))
                .andExpect(redirectedUrl("/schedules"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/schedules"));

        assertTrue(isDeleted);
        verify(scheduleService).deleteSchedule(4);
    }
}
