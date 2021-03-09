package com.fedorov.spring.repository;

import com.fedorov.spring.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    Schedule createSchedule(Integer id, String stringDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        Group group = new Group(4, "er-48");
        Teacher teacher = new Teacher(1, "David", "Kage");
        Course course = new Course(1, teacher, "Astronomy");
        TimeTable timeTable = new TimeTable(1, "Today Table");

        return new Schedule(id, group, course, teacher, timeTable, date);
    }

    @Test
    void getAllSchedulesByDate_listOfSchedulesIsExpected() throws ParseException {
        String stringDate = "2020-11-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<Schedule> expectedSchedules = Arrays.asList(
                createSchedule(1,"2020-11-19")
        );
        List<Schedule> actualSchedules = scheduleRepository.findAllByDate(date);

        assertThat(expectedSchedules, containsInAnyOrder(actualSchedules.toArray()));
    }

    @Test
    void getSchedulesByDateAndGroupId_listOfSchedulesIsExpected() throws ParseException {
        String stringDate = "2020-11-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<Schedule> expectedSchedules = new ArrayList<>();
        expectedSchedules.add(createSchedule(1, "2020-11-19"));
        List<Schedule> actualSchedules = scheduleRepository.findAllByGroupIdAndDate(4, date);

        assertThat(expectedSchedules, containsInAnyOrder(actualSchedules.toArray()));
    }

    @Test
    void getSchedulesByMonthAndGroupId_listOfSchedulesIsExpected() throws ParseException {
        List<Schedule> expectedSchedules = new ArrayList<>();
        expectedSchedules.add(createSchedule(1, "2020-11-19"));
        List<Schedule> actualSchedules = scheduleRepository.findAllByGroupIdAndMonth(4, 11);

        assertThat(expectedSchedules, containsInAnyOrder(actualSchedules.toArray()));
    }

    @Test
    void getSchedulesByDateAndTeacherId_listOFSchedulesIsExpected() throws ParseException {
        String stringDate = "2020-12-19";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        List<Schedule> expectedSchedules = new ArrayList<>();
        expectedSchedules.add(createSchedule(2, stringDate));
        List<Schedule> actualSchedules = scheduleRepository.findAllByTeacherIdAndDate(1, date);

        assertThat(expectedSchedules, containsInAnyOrder(actualSchedules.toArray()));
    }

    @Test
    void getSchedulesByMonthAndTeacherId_listOfSchedulesIsExpected() throws ParseException {
        List<Schedule> expectedSchedules = new ArrayList<>();
        expectedSchedules.add(createSchedule(1, "2020-11-19"));
        List<Schedule> actualSchedules = scheduleRepository.findAllByTeacherIdAndMonth(1, 11);

        assertThat(expectedSchedules, containsInAnyOrder(actualSchedules.toArray()));
    }
}
