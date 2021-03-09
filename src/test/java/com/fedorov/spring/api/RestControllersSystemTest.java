package com.fedorov.spring.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedorov.spring.service.dto.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@SpringBootTest
public class RestControllersSystemTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetAllCoursesRestController_coursesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(allOf(
                          containsString("Astronomy"),
                          containsString("History"),
                          containsString("Programming")
                )));
    }

    @Test
    public void testGetAllGroupsRestController_groupsRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/groups/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(allOf(
                        containsString("ef-41"),
                        containsString("ed-48"),
                        containsString("TO-36"),
                        containsString("er-48")
                )));
    }

    @Test
    public void testGetAllStudentsRestController_studentsRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(allOf(
                        containsString("Betty"),
                        containsString("Paul"),
                        containsString("Thomas"),
                        containsString("Mary")
                )));
    }

    @Test
    public void testGetAllTeachersRestController_teachersRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(allOf(
                        containsString("David"),
                        containsString("Robert"),
                        containsString("Roberto"),
                        containsString("Bob")
                )));
    }

    @Test
    public void testGetAllSchedulesRestController_schedulesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedules/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(allOf(
                        containsString("2020-11-19"),
                        containsString("2020-12-19"),
                        containsString("2020-07-30"),
                        containsString("2020-07-30")
                )));
    }

    @Test
    public void testGetAllByGroupIdAndDateSchedulesRestController_schedulesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedules/all/group-date/2/2020-07-30"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("2020-07-30")));
    }

    @Test
    public void testGetAllByGroupIdAndMonthSchedulesRestController_schedulesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedules/all/group-month/2/7"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("2020-07-30")));
    }

    @Test
    public void testGetAllByTeacherIdAndDateSchedulesRestController_schedulesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedules/all/teacher-date/2/2020-07-30"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("2020-07-30")));
    }

    @Test
    public void testGetAllByTeacherIdAndMonthSchedulesRestController_schedulesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedules/all/teacher-month/2/7"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("2020-07-30")));
    }

    @Test
    public void testGetAllTimeTablesRestController_timeTablesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/timeTables/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(allOf(
                        containsString("Today Table"),
                        containsString("Monday Table"),
                        containsString("Thuesday Table")
                )));
    }

    @Test
    public void testGetSingleCoursesRestController_coursesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/single/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("History")));
    }

    @Test
    public void testGetSingleGroupsRestController_groupsRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/groups/single/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("ed-48")));
    }

    @Test
    public void testGetSingleStudentsRestController_studentsRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/single/3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Thomas")));
    }

    @Test
    public void testGetSingleTeachersRestController_teacherRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/single/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Robert")));
    }

    @Test
    public void testGetSingleSchedulesRestController_schedulesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedules/single/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("2020-12-19")));
    }

    @Test
    public void testGetSingleTimeTablesRestContoller_timeTablesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/timeTables/single/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("2020-12-15")));
    }

    @Test
    public void testAddCoursesRestController_coursesRestControllerShouldResponse() throws Exception {
        CourseDTO savedCourse = new CourseDTO(new TeacherDTO(0, "Mock", "Me"), "Java");

        mockMvc.perform(MockMvcRequestBuilders.post("/courses/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(savedCourse)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testAddGroupsRestController_groupsRestControllerShouldResponse() throws Exception {
        GroupDTO savedGroup = new GroupDTO("jj-77");

        mockMvc.perform(MockMvcRequestBuilders.post("/groups/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(savedGroup)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testAddStudentsRestController_studentsRestControllerShouldResponse() throws Exception {
        StudentDTO savedStudent = new StudentDTO(new GroupDTO(0, ""), "Egor", "Fedorov");

        mockMvc.perform(MockMvcRequestBuilders.post("/students/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(savedStudent)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testAddTeachersRestController_teachersRestControllerShouldResponse() throws Exception {
        TeacherDTO savedTeacher = new TeacherDTO("Nick", "Williams");

        mockMvc.perform(MockMvcRequestBuilders.post("/teachers/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(savedTeacher)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testAddSchedulesRestController_schedulesRestControllerShouldResponse() throws Exception {
        String stringDate = "2021-02-11";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        ScheduleDTO savedSchedule = new ScheduleDTO(new GroupDTO(0, ""),
                        new CourseDTO(0, ""),
                        new TeacherDTO(0, "", ""),
                        new TimeTableDTO(0, ""),
                        date);

        mockMvc.perform(MockMvcRequestBuilders.post("/schedules/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(savedSchedule)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testAddTimeTablesRestController_timeTablesRestControllerShouldResponse() throws Exception {
        TimeTableDTO savedTimeTable = new TimeTableDTO("New timetable");

        mockMvc.perform(MockMvcRequestBuilders.post("/timeTables/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(savedTimeTable)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdateCoursesRestController_coursesRestControllerShouldResponse() throws Exception {
        CourseDTO updatedCourse = new CourseDTO(1, new TeacherDTO(0, "", ""), "NewName");

        mockMvc.perform(MockMvcRequestBuilders.put("/courses/update/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedCourse)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateGroupsRestController_groupsRestControllerShouldResponse() throws Exception {
        GroupDTO updatedGroup = new GroupDTO(1, "NewName");

        mockMvc.perform(MockMvcRequestBuilders.put("/groups/update/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedGroup)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateStudentsRestController_studentsRestControllerShouldResponse() throws Exception {
        StudentDTO updatedStudent = new StudentDTO(1, new GroupDTO(0, ""), "New", "Name");

        mockMvc.perform(MockMvcRequestBuilders.put("/students/update/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateTeachersRestController_teachersRestControllerShouldResponse() throws Exception {
        TeacherDTO updateTeacher = new TeacherDTO(1, "New", "Name");

        mockMvc.perform(MockMvcRequestBuilders.put("/teachers/update/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updateTeacher)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateSchedulesRestController_schedulesRestControllerShouldResponse() throws Exception {
        String stringDate = "2021-02-11";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);

        ScheduleDTO updatedSchedule = new ScheduleDTO(1, new GroupDTO(0 ,""),
                new CourseDTO(0, ""),
                new TeacherDTO(0 , "", ""),
                new TimeTableDTO(0 , ""), date);

        mockMvc.perform(MockMvcRequestBuilders.put("/schedules/update/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedSchedule)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateTimeTablesRestController_timeTablesRestControllerShouldResponse() throws Exception {
        TimeTableDTO updatedTimeTable = new TimeTableDTO(1, "New Name");

        mockMvc.perform(MockMvcRequestBuilders.put("/timeTables/update/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedTimeTable)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteCoursesRestController_coursesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/courses/delete/3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteGroupsRestController_groupsRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/groups/delete/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteStudentsRestController_studentsRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/delete/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteTeachersRestController_teachersRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/teachers/delete/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteSchedulesRestController_schedulesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/schedules/delete/3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteTimeTablesRestController_timeTablesRestControllerShouldResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/timeTables/delete/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

