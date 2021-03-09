package com.fedorov.spring.controllers;

import com.fedorov.spring.service.ScheduleService;
import com.fedorov.spring.service.TimeTableService;
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
public class TimeTablesControllerTest {

    private MockMvc mockMvc;

    @Mock
    TimeTableService timeTableService;
    @Mock
    ScheduleService scheduleService;

    @InjectMocks
    TimeTablesController timeTablesController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(timeTablesController).build();
    }

    @Test
    public void selectAllTimeTables_allTimeTablesPageShown() throws Exception {
        List<TimeTableDTO> expectedTimeTables = Arrays.asList(new TimeTableDTO(1, "Monday"));

        when(timeTableService.selectAllTimeTables()).thenReturn(expectedTimeTables);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/timeTables"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("timetables/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("timetables"))
                .andExpect(MockMvcResultMatchers.model().attribute("timetables", expectedTimeTables));

        verify(timeTableService).selectAllTimeTables();
    }

    @Test
    public void selectTimeTableById_singleTimeTablePageShown() throws Exception {
        String stringDate = "2020-12-18";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        TimeTableDTO expectedTimeTable = new TimeTableDTO(5, "Friday", date);
        List<ScheduleDTO> expectedSchedules = Arrays.asList(new ScheduleDTO(1,
                new GroupDTO(), new CourseDTO(), new TeacherDTO(), expectedTimeTable, date));

        when(timeTableService.selectTimeTable(5)).thenReturn(expectedTimeTable);
        when(scheduleService.selectAllSchedulesByDate(date)).thenReturn(expectedSchedules);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/timeTables/5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("timetables/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("timetable"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("schedules"))
                .andExpect(MockMvcResultMatchers.model().attribute("timetable", expectedTimeTable))
                .andExpect(MockMvcResultMatchers.model().attribute("schedules", expectedSchedules));

        verify(timeTableService).selectTimeTable(5);
        verify(scheduleService).selectAllSchedulesByDate(date);
    }

    @Test
    public void newTimeTable_newTimeTablePageShown() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/timeTables/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("timetables/new"));
    }

    @Test
    public void createNewTimeTable_newTimeTableCreatedAndPageRedirected() throws Exception {
        String stringDate = "2020-12-18";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        TimeTableDTO expectedTimeTable = new TimeTableDTO(5, "Friday", date);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/timeTables")
                        .param("id", "5")
                        .param("tableName", "Friday")
                        .param("date", "2020-12-18"))
                .andExpect(redirectedUrl("/timeTables"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/timeTables"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("timetable"))
                .andExpect(MockMvcResultMatchers.model().attribute("timetable", expectedTimeTable));
    }

    @Test
    public void editTimeTable_editPageShown() throws Exception {
        String stringDate = "2020-12-18";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        TimeTableDTO expectedTimeTable = new TimeTableDTO(5, "Friday", date);

        when(timeTableService.selectTimeTable(5)).thenReturn(expectedTimeTable);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/timeTables/5/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("timetables/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("timetable"))
                .andExpect(MockMvcResultMatchers.model().attribute("timetable", expectedTimeTable));

        verify(timeTableService).selectTimeTable(5);
    }

    @Test
    public void updateTimeTable_timeTableUpdatedAndPageRedirected() throws Exception {
        String stringDate = "2020-12-17";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        TimeTableDTO expectedTimeTable = new TimeTableDTO(4, "Thursday", date);
        boolean isUpdated;
        when(timeTableService.updateTimeTable(expectedTimeTable)).thenReturn(isUpdated = true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.patch("/timeTables/4")
                        .param("id", "4")
                        .param("tableName", "Thursday")
                        .param("date", "2020-12-17"))
                .andExpect(redirectedUrl("/timeTables"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/timeTables"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("timetable"))
                .andExpect(MockMvcResultMatchers.model().attribute("timetable", expectedTimeTable));

        assertTrue(isUpdated);
        verify(timeTableService).updateTimeTable(expectedTimeTable);
    }

    @Test
    public void deleteTimeTable_timeTableDeletedAndPageRedirected() throws Exception {
        boolean isDeleted;
        when(timeTableService.deleteTimeTable(3)).thenReturn(isDeleted = true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/timeTables/3"))
                .andExpect(redirectedUrl("/timeTables"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/timeTables"));

        assertTrue(isDeleted);
        verify(timeTableService).deleteTimeTable(3);
    }
}
