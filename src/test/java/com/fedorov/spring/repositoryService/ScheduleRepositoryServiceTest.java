package com.fedorov.spring.repositoryService;

import com.fedorov.spring.mappers.ScheduleMapper;
import com.fedorov.spring.model.*;
import com.fedorov.spring.repository.ScheduleRepository;
import com.fedorov.spring.service.ScheduleService;
import com.fedorov.spring.service.dto.ScheduleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScheduleRepositoryServiceTest {

    @Mock
    ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    public Schedule createSchedule(Date date) {
        Group group = new Group(1, "group");
        Teacher teacher = new Teacher(2, "Teacher", "Two");
        Course course = new Course(4, teacher, "course");
        TimeTable timeTable = new TimeTable(1, "Monday");

        return new Schedule(group, course, teacher,timeTable,date);
    }

    @Test
    void getAllSchedules_listOfAllSchedulesIsExpected() throws ParseException {
        String stringDate = "2020-11-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<Schedule> expectedSchedules = Arrays.asList(
                createSchedule(date)
        );

        when(scheduleRepository.findAll()).thenReturn(expectedSchedules);
        List<ScheduleDTO> actualSchedules = scheduleService.selectAllSchedules();
        String actualSchedulesElementsClass = actualSchedules.get(0).getClass().getSimpleName();

        assertEquals("ScheduleDTO", actualSchedulesElementsClass);
        verify(scheduleRepository).findAll();
    }

    @Test
    void getAllSchedulesByDate_listOfSchedulesIsExpected() throws ParseException {
        String stringDate = "2020-11-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<Schedule> expectedSchedules = Arrays.asList(
                createSchedule(date)
        );

        when(scheduleRepository.findAllByDate(date)).thenReturn(expectedSchedules);
        List<ScheduleDTO> actualSchedules = scheduleService.selectAllSchedulesByDate(date);
        String actualSchedulesElementsClass = actualSchedules.get(0).getClass().getSimpleName();

        assertEquals("ScheduleDTO", actualSchedulesElementsClass);
        verify(scheduleRepository).findAllByDate(date);
    }

    @Test
    void getScheduleById_singleScheduleIsExpected() throws ParseException {
        String stringDate = "2021-10-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        Schedule expectedSchedule = createSchedule(date);
        when(scheduleRepository.getOne(1)).thenReturn(expectedSchedule);
        ScheduleDTO actualSchedule = scheduleService.selectSchedule(1);
        String actualScheduleClass = actualSchedule.getClass().getSimpleName();

        assertEquals("ScheduleDTO", actualScheduleClass);
        verify(scheduleRepository).getOne(1);
    }

    @Test
    void getAllSchedulesByGroupIdAndDate_listOfSchedulesIsExpected() throws ParseException {
        String stringDate = "2020-11-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<Schedule> expectedSchedules = Arrays.asList(
                createSchedule(date)
        );

        when(scheduleRepository.findAllByGroupIdAndDate(1, date)).thenReturn(expectedSchedules);
        List<ScheduleDTO> actualSchedules = scheduleService.selectScheduleByGroupIdAndDate(1, date);
        String actualSchedulesElementsClass = actualSchedules.get(0).getClass().getSimpleName();

        assertEquals("ScheduleDTO", actualSchedulesElementsClass);
        verify(scheduleRepository).findAllByGroupIdAndDate(1, date);
    }

    @Test
    void getAllSchedulesByTeacherIdAndDate_listOfSchedulesIsExpected() throws ParseException {
        String stringDate = "2020-11-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<Schedule> expectedSchedules = Arrays.asList(
                createSchedule(date)
        );

        when(scheduleRepository.findAllByTeacherIdAndDate(2, date)).thenReturn(expectedSchedules);
        List<ScheduleDTO> actualSchedules = scheduleService.selectScheduleByTeacherIdAndDate(2, date);
        String actualSchedulesElementsClass = actualSchedules.get(0).getClass().getSimpleName();

        assertEquals("ScheduleDTO", actualSchedulesElementsClass);
        verify(scheduleRepository).findAllByTeacherIdAndDate(2, date);
    }

    @Test
    void getAllSchedulesByMonthAndGroupId_listOfSchedulesIsExpected() throws ParseException {
        String stringDate = "2020-11-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<Schedule> expectedSchedules = Arrays.asList(
                createSchedule(date)
        );

        when(scheduleRepository.findAllByGroupIdAndMonth(1, 11)).thenReturn(expectedSchedules);
        List<ScheduleDTO> actualSchedules = scheduleService.selectSchedulesByMonthAndGroupId(1, 11);
        String actualSchedulesElementsClass = actualSchedules.get(0).getClass().getSimpleName();

        assertEquals("ScheduleDTO", actualSchedulesElementsClass);
        verify(scheduleRepository).findAllByGroupIdAndMonth(1, 11);
    }

    @Test
    void getAllSchedulesByMonthAndTeacherId_listOfSchedulesIsExpected() throws ParseException {
        String stringDate = "2020-09-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<Schedule> expectedSchedules = Arrays.asList(
                createSchedule(date)
        );

        when(scheduleRepository.findAllByTeacherIdAndMonth(2, 9)).thenReturn(expectedSchedules);
        List<ScheduleDTO> actualSchedules = scheduleService.selectSchedulesByMonthAndTeacherId(2, 9);
        String actualSchedulesElementsClass = actualSchedules.get(0).getClass().getSimpleName();

        assertEquals("ScheduleDTO", actualSchedulesElementsClass);
        verify(scheduleRepository).findAllByTeacherIdAndMonth(2, 9);
    }

    @Test
    void deleteScheduleById_scheduleIsExpectedToBeDeleted() {
        boolean isDeleted;

        isDeleted = scheduleService.deleteSchedule(5);

        assertTrue(isDeleted);
        verify(scheduleRepository).deleteById(5);
    }

    @Test
    void updateSchedule_scheduleIsExpectedToBeUpdated() throws ParseException {
        boolean isUpdated;

        String stringDate = "2020-09-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        Schedule schedule = createSchedule(date);

        ScheduleDTO scheduleDTO = ScheduleMapper.INSTANCE.scheduleToDTO(schedule);

        isUpdated = scheduleService.updateSchedule(scheduleDTO);

        assertTrue(isUpdated);
        verify(scheduleRepository).save(schedule);
    }

    @Test
    void insertSchedule_scheduleIsExpectedToBeCreatedIsDataBase() throws ParseException {
        String stringDate = "2020-09-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        Schedule schedule = createSchedule(date);
        ScheduleDTO scheduleDTO = ScheduleMapper.INSTANCE.scheduleToDTO(schedule);
        scheduleService.insertSchedule(scheduleDTO);
        verify(scheduleRepository).save(createSchedule(date));
    }

}
