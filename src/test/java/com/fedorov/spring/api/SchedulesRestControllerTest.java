package com.fedorov.spring.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedorov.spring.model.*;
import com.fedorov.spring.service.ScheduleService;
import com.fedorov.spring.service.dto.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(SchedulesRestController.class)
public class SchedulesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ScheduleService scheduleService;

    public ScheduleDTO createSchedule(Date date) {
        GroupDTO group = new GroupDTO(1, "group");
        TeacherDTO teacher = new TeacherDTO(2, "Teacher", "Two");
        CourseDTO course = new CourseDTO(4, teacher, "course");
        TimeTableDTO timeTable = new TimeTableDTO(1, "Monday");

        return new ScheduleDTO(group, course, teacher,timeTable,date);
    }

    @Test
    public void selectAllSchedules_allSchedulesExpected() throws Exception {
        String stringDate = "2020-11-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<ScheduleDTO> expectedSchedules = Arrays.asList(
                createSchedule(date)
        );

        Mockito.when(scheduleService.selectAllSchedules()).thenReturn(expectedSchedules);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/schedules/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedSchedules);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(scheduleService).selectAllSchedules();
    }

    @Test
    public void getSingleSchedule_singleScheduleExpected() throws Exception {
        String stringDate = "2020-11-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        ScheduleDTO expectedSchedule = createSchedule(date);

        Mockito.when(scheduleService.selectSchedule(1)).thenReturn(expectedSchedule);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/schedules/single/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedSchedule);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(scheduleService).selectSchedule(1);
    }

    @Test
    public void getSchedulesListByGroupIdAndDate_listOfSchedulesExpected() throws Exception {
        String stringDate = "2020-11-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<ScheduleDTO> expectedSchedules = Arrays.asList(
                createSchedule(date)
        );

        Mockito.when(scheduleService.selectScheduleByGroupIdAndDate(1, date)).thenReturn(expectedSchedules);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/schedules/all/group-date/1/2020-11-19"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedSchedules);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(scheduleService).selectScheduleByGroupIdAndDate(1, date);
    }

    @Test
    public void getSchedulesListByGroupIdAndMonthNo_listOfSchedulesExpected() throws Exception {
        String stringDate = "2020-12-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<ScheduleDTO> expectedSchedules = Arrays.asList(
                createSchedule(date)
        );

        Mockito.when(scheduleService.selectSchedulesByMonthAndGroupId(1, 12)).thenReturn(expectedSchedules);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/schedules/all/group-month/1/12"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedSchedules);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(scheduleService).selectSchedulesByMonthAndGroupId(1,12);
    }

    @Test
    public void getSchedulesListByTeacherIdAndDate_listOfSchedulesExpected() throws Exception {
        String stringDate = "2020-01-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<ScheduleDTO> expectedSchedules = Arrays.asList(
                createSchedule(date)
        );

        Mockito.when(scheduleService.selectScheduleByTeacherIdAndDate(2, date)).thenReturn(expectedSchedules);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/schedules/all/teacher-date/2/2020-01-19"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedSchedules);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(scheduleService).selectScheduleByTeacherIdAndDate(2, date);
    }

    @Test
    public void getSchedulesListByTeacherIdAndMonthNo_listOfSchedulesExpected() throws Exception {
        String stringDate = "2021-02-11";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<ScheduleDTO> expectedSchedules = Arrays.asList(
                createSchedule(date)
        );

        Mockito.when(scheduleService.selectSchedulesByMonthAndTeacherId(2, 2)).thenReturn(expectedSchedules);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/schedules/all/teacher-month/2/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedSchedules);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(scheduleService).selectSchedulesByMonthAndTeacherId(2,2);
    }

    @Test
    public void addSchedule_newScheduleIsExpectedToBeAdded() throws Exception {
        String stringDate = "2021-02-11";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        ScheduleDTO savedScheduleDTO = createSchedule(date);

        scheduleService.insertSchedule(savedScheduleDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/schedules/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(savedScheduleDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateSchedule_scheduleIsExpectedToBeUpdated() throws Exception {
        String stringDate = "2019-03-11";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        ScheduleDTO scheduleDTOThatWillBeUpdated = createSchedule(date);

        mockMvc.perform(MockMvcRequestBuilders.put("/schedules/update/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scheduleDTOThatWillBeUpdated)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteSchedule_scheduleIsExpectedToBeDeleted() throws Exception {
        Mockito.when(scheduleService.deleteSchedule(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/schedules/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

