package com.fedorov.spring.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedorov.spring.service.TimeTableService;
import com.fedorov.spring.service.dto.TimeTableDTO;
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

@WebMvcTest(TimeTablesRestController.class)
public class TimeTablesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TimeTableService timeTableService;

    @Test
    public void selectAllTimeTables_allTimeTablesExpected() throws Exception {
        List<TimeTableDTO> expectedTimeTables = Arrays.asList(
                new TimeTableDTO(1, "Today's timetable"),
                new TimeTableDTO(2, "Tomorrow's timetable")
        );

        Mockito.when(timeTableService.selectAllTimeTables()).thenReturn(expectedTimeTables);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/timeTables/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedTimeTables);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(timeTableService).selectAllTimeTables();
    }

    @Test
    public void getSingleTimeTable_singleTimeTableIsExpected() throws Exception {
        TimeTableDTO expectedTimeTable = new TimeTableDTO(1, "Today's timetable");

        Mockito.when(timeTableService.selectTimeTable(1)).thenReturn(expectedTimeTable);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/timeTables/single/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedTimeTable);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(timeTableService).selectTimeTable(1);
    }

    @Test
    public void addTimeTable_newTimeTableExpectedToBeAdded() throws Exception {
        TimeTableDTO savedTimeTable = new TimeTableDTO(3, "Wednesday");

        timeTableService.insertTimeTable(savedTimeTable);

        mockMvc.perform(MockMvcRequestBuilders.post("/timeTables/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(savedTimeTable)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateTimeTable_timeTableExpectedToBeUpdated() throws Exception {
        TimeTableDTO timeTableDTOThatWillBeUpdated = new TimeTableDTO(4, "Thursday");

        mockMvc.perform(MockMvcRequestBuilders.put("/timeTables/update/4")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(timeTableDTOThatWillBeUpdated)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteTimeTable_timeTableExpectedToBeDeleted() throws Exception {
        Mockito.when(timeTableService.deleteTimeTable(4)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/timeTables/delete/4"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

