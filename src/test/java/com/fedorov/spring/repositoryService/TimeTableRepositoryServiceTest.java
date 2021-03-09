package com.fedorov.spring.repositoryService;

import com.fedorov.spring.model.TimeTable;
import com.fedorov.spring.repository.TimeTableRepository;
import com.fedorov.spring.service.TimeTableService;
import com.fedorov.spring.service.dto.TimeTableDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimeTableRepositoryServiceTest {

    @Mock
    TimeTableRepository timeTableRepository;

    @InjectMocks
    private TimeTableService timeTableService;

    @Test
    void getAllTimeTables_listOfAllTimeTablesIsExpected() {
        List<TimeTable> expectedTimeTables = Arrays.asList(
                new TimeTable(1, "Today's timetable"),
                new TimeTable(2, "Tomorrow's timetable")
        );

        when(timeTableRepository.findAll()).thenReturn(expectedTimeTables);
        List<TimeTableDTO> actualTimeTables = timeTableService.selectAllTimeTables();
        String actualTimeTablesElementsClass = actualTimeTables.get(0).getClass().getSimpleName();

        assertEquals("TimeTableDTO", actualTimeTablesElementsClass);
        verify(timeTableRepository).findAll();
    }

    @Test
    void getTimeTableById_singleTimeTableIsExpected() {
        TimeTable expectedTimeTable = new TimeTable(5, "Friday's timetable");

        when(timeTableRepository.getOne(5)).thenReturn(expectedTimeTable);
        TimeTableDTO actualTimeTable = timeTableService.selectTimeTable(5);
        String actualTimeTableClass = actualTimeTable.getClass().getSimpleName();

        assertEquals("TimeTableDTO", actualTimeTableClass);
        verify(timeTableRepository).getOne(5);
    }

    @Test
    void deleteTimeTableById_timeTableExpectedToBeDeleted() {
        boolean isDeleted;

        isDeleted = timeTableService.deleteTimeTable(4);

        assertTrue(isDeleted);
        verify(timeTableRepository).deleteById(4);
    }

    @Test
    void updateTimeTable_timeTableExpectedToBeUpdated() {
        boolean isUpdated;

        TimeTable timeTable = new TimeTable(1, "Today's timetable");
        TimeTableDTO timeTableDTO = new TimeTableDTO(1, "Today's timetable");
        isUpdated = timeTableService.updateTimeTable(timeTableDTO);

        assertTrue(isUpdated);
        verify(timeTableRepository).save(timeTable);
    }

    @Test
    void insertTimeTable_timeTableExpectedToBeCreatedInDataBase() {
        TimeTable timeTable = new TimeTable(4, "Thursday's timetable");
        TimeTableDTO timeTableDTO = new TimeTableDTO(4, "Thursday's timetable");
        timeTableService.insertTimeTable(timeTableDTO);
        verify(timeTableRepository).save(timeTable);
    }
}

