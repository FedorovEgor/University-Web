package com.fedorov.spring.service;

import com.fedorov.spring.service.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniversityManagementTest {

    @Mock
    StudentService studentService;
    @Mock
    CourseService courseService;
    @Mock
    GroupService groupService;
    @Mock
    TeacherService teacherService;
    @Mock
    TimeTableService timeTableService;

    @InjectMocks
    private UniversityManagement universityManagement;

    @Test
    void getStudentById_singleStudentIsExpected() {
        StudentDTO expectedStudent = new StudentDTO(1, new GroupDTO(1, "jk-44"), "Bob", "Moor");
        when(studentService.selectStudent(1)).thenReturn(expectedStudent);
        StudentDTO actualStudent = universityManagement.getStudent(1);

        assertEquals(expectedStudent, actualStudent);
        verify(studentService).selectStudent(1);
    }

    @Test
    void getAllStudents_listOfAllStudentsIsExpected() {
        List<StudentDTO> expectedStudents = Arrays.asList(
                new StudentDTO(1, new GroupDTO(1, "jk-44"), "Mike", "Marris"),
                new StudentDTO(2, new GroupDTO(4, "mc-31"), "Rob", "Rhykers")
        );

        when(studentService.selectAllStudents()).thenReturn(expectedStudents);
        List<StudentDTO> actualStudents = universityManagement.getAllStudents();

        assertThat(expectedStudents, containsInAnyOrder(actualStudents.toArray()));
        verify(studentService).selectAllStudents();
    }

    @Test
    void expelStudentById_studentsExpectedToBeDeleted() {
        boolean isDeleted;

        when(studentService.deleteStudent(4)).thenReturn(true);
        isDeleted = universityManagement.expelStudent(4);

        assertTrue(isDeleted);
        verify(studentService).deleteStudent(4);
    }

    @Test
    void updateStudent_studentExpectedToBeUpdated() {
        boolean isUpdated;

        StudentDTO studentDTO = new StudentDTO(new GroupDTO(5, "ef-55"), "Maria", "Edwards");
        when(studentService.updateStudent(studentDTO)).thenReturn(true);
        isUpdated = universityManagement.updateStudent(studentDTO);

        assertTrue(isUpdated);
        verify(studentService).updateStudent(studentDTO);
    }

    @Test
    void addStudent_studentExpectedToBeCreatedInDataBase() {
        StudentDTO studentDTO = new StudentDTO(1, new GroupDTO(6, "em-56"), "Linda", "Cook");
        universityManagement.addStudent(studentDTO);
        verify(studentService).insertStudent(studentDTO);
    }

    @Test
    void getCourseById_singleCourseIsExpected() {
        CourseDTO expectedCourse = new CourseDTO(1, new TeacherDTO(5, "Helen", "Carter"), "Java");
        when(courseService.selectCourse(1)).thenReturn(expectedCourse);
        CourseDTO actualCourse = universityManagement.getCourse(1);

        assertEquals(expectedCourse, actualCourse);
        verify(courseService).selectCourse(1);
    }

    @Test
    void getAllCourses_listOfAllCoursesIsExpected() {
        List<CourseDTO> expectedCourses = Arrays.asList(
                new CourseDTO(1, new TeacherDTO(2, "Hob", "Robbinson"), "History"),
                new CourseDTO(2, new TeacherDTO(3, "Jake", "Groove"), "Maths"),
                new CourseDTO(3, new TeacherDTO(5, "Tom", "Curtis"), "English")
        );

        when(courseService.selectAllCourses()).thenReturn(expectedCourses);
        List<CourseDTO> actualCourses = universityManagement.getAllCourses();

        assertThat(expectedCourses, containsInAnyOrder(actualCourses.toArray()));
        verify(courseService).selectAllCourses();
    }

    @Test
    void deleteCourseById_courseExpectedToBeDeleted() {
        boolean isDeleted;

        when(courseService.deleteCourse(4)).thenReturn(true);
        isDeleted = universityManagement.deleteCourse(4);

        assertTrue(isDeleted);
        verify(courseService).deleteCourse(4);
    }

    @Test
    void updateCourse_courseExpectedToBeUpdated() {
        boolean isUpdated;

        CourseDTO courseDTO = new CourseDTO(1, new TeacherDTO(2, "Hob", "Robbinson"), "Python");
        when(courseService.updateCourse(courseDTO)).thenReturn(true);
        isUpdated = universityManagement.updateCourse(courseDTO);

        assertTrue(isUpdated);
        verify(courseService).updateCourse(courseDTO);
    }

    @Test
    void addCourse_courseExpectedToBeCreatedInDataBase() {
        CourseDTO courseDTO = new CourseDTO(new TeacherDTO(1, "Bob", "Rob"), "First Aid");
        universityManagement.addCourse(courseDTO);
        verify(courseService).insertCourse(courseDTO);
    }

    @Test
    void getGroupById_singleGroupIsExpected() {
        GroupDTO expectedGroup = new GroupDTO(1, "ft-01");
        when(groupService.selectGroup(1)).thenReturn(expectedGroup);
        GroupDTO actualGroup = universityManagement.getGroup(1);;

        assertEquals(expectedGroup, actualGroup);
        verify(groupService).selectGroup(1);
    }

    @Test
    void getAllGroups_listOfAllGroupsIsExpected() {
        List<GroupDTO> expectedGroups = Arrays.asList(
                new GroupDTO(1, "ff-58"),
                new GroupDTO(2, "em-67")
        );

        when(groupService.selectAllGroups()).thenReturn(expectedGroups);
        List<GroupDTO> actualGroups = universityManagement.getAllGroups();

        assertThat(expectedGroups, containsInAnyOrder(actualGroups.toArray()));
        verify(groupService).selectAllGroups();
    }

    @Test
    void disbandGroupById_groupExpectedToBeDeleted() {
        boolean isDeleted;

        when(groupService.deleteGroup(7)).thenReturn(true);
        isDeleted = universityManagement.disbandGroup(7);

        assertTrue(isDeleted);
        verify(groupService).deleteGroup(7);
    }

    @Test
    void updateGroup_groupExpectedToBeUpdated() {
        boolean isUpdated;

        GroupDTO groupDTO = new GroupDTO(7, "ee-44");
        when(groupService.updateGroup(groupDTO)).thenReturn(true);
        isUpdated = universityManagement.updateGroup(groupDTO);

        assertTrue(isUpdated);
        verify(groupService).updateGroup(groupDTO);
    }

    @Test
    void addGroup_groupExpectedToBeCreatedInDataBase() {
        GroupDTO groupDTO = new GroupDTO(1, "gm-56");
        universityManagement.addGroup(groupDTO);
        verify(groupService).insertGroup(groupDTO);
    }

    @Test
    void getTeacherById_singleTeacherIsExpected() {
        TeacherDTO expectedTeacher = new TeacherDTO(7, "Josh", "Smith");
        when(teacherService.selectTeacher(7)).thenReturn(expectedTeacher);
        TeacherDTO actualTeacher = universityManagement.getTeacher(7);

        assertEquals(expectedTeacher, actualTeacher);
        verify(teacherService).selectTeacher(7);
    }

    @Test
    void getAllTeachers_listOfAllTeachersIsExpected() {
        List<TeacherDTO> expectedTeachers = Arrays.asList(
                new TeacherDTO(1, "Paul", "Howard"),
                new TeacherDTO(4, "Thomas", "Reed")
        );

        when(teacherService.selectAllTeachers()).thenReturn(expectedTeachers);
        List<TeacherDTO> actualTeachers = universityManagement.getAllTeachers();

        assertThat(expectedTeachers, containsInAnyOrder(actualTeachers.toArray()));
        verify(teacherService).selectAllTeachers();
    }

    @Test
    void retireTeacherById_teacherExpectedToBeDeleted() {
        boolean isDeleted;

        when(teacherService.deleteTeacher(6)).thenReturn(true);
        isDeleted = universityManagement.retireTeacher(6);

        assertTrue(isDeleted);
        verify(teacherService).deleteTeacher(6);
    }

    @Test
    void updateTeacher_teacherExpectedToBeUpdated() {
        boolean isUpdated;

        TeacherDTO teacherDTO = new TeacherDTO(5, "Mike", "Daniels");
        when(teacherService.updateTeacher(teacherDTO)).thenReturn(true);
        isUpdated = universityManagement.updateTeacher(teacherDTO);

        assertTrue(isUpdated);
        verify(teacherService).updateTeacher(teacherDTO);
    }

    @Test
    void addTeacher_teacherExpectedToBeCreatedInDataBase() {
        TeacherDTO teacherDTO = new TeacherDTO(5, "Mike", "Daniels");
        universityManagement.addTeacher(teacherDTO);
        verify(teacherService).insertTeacher(teacherDTO);
    }

    @Test
    void getTimeTableById_singleTimeTableIsExpected() {
        TimeTableDTO expectedTimeTable = new TimeTableDTO(5, "Friday's timetable");

        when(timeTableService.selectTimeTable(5)).thenReturn(expectedTimeTable);
        TimeTableDTO actualTimeTable = universityManagement.getTimeTable(5);

        assertEquals(expectedTimeTable, actualTimeTable);
        verify(timeTableService).selectTimeTable(5);
    }

    @Test
    void getAllTimeTables_listOfAllTimeTablesIsExpected() {
        List<TimeTableDTO> expectedTimeTables = Arrays.asList(
                new TimeTableDTO(1, "Today's timetable"),
                new TimeTableDTO(2, "Tomorrow's timetable")
        );

        when(timeTableService.selectAllTimeTables()).thenReturn(expectedTimeTables);
        List<TimeTableDTO> actualTimeTables = universityManagement.getAllTimeTables();

        assertThat(expectedTimeTables, containsInAnyOrder(actualTimeTables.toArray()));
        verify(timeTableService).selectAllTimeTables();
    }

    @Test
    void deleteTimeTableById_timeTableExpectedToBeDeleted() {
        boolean isDeleted;

        when(timeTableService.deleteTimeTable(1)).thenReturn(true);
        isDeleted = universityManagement.deleteTimeTable(1);

        assertTrue(isDeleted);
        verify(timeTableService).deleteTimeTable(1);
    }

    @Test
    void updateTimeTable_timeTableExpectedToBEUpdated() {
        boolean isUpdated;

        TimeTableDTO timeTableDTO = new TimeTableDTO(6, "Saturday's timetable");
        when(timeTableService.updateTimeTable(timeTableDTO)).thenReturn(true);
        isUpdated = universityManagement.updateTimeTable(timeTableDTO);

        assertTrue(isUpdated);
        verify(timeTableService).updateTimeTable(timeTableDTO);
    }

    @Test
    void createTimeTable_newTimeTableExpectedToBeCreated() {
        TimeTableDTO timeTableDTO = new TimeTableDTO(4, "Thursday's timetable");
        universityManagement.createTimeTable(timeTableDTO);
        verify(timeTableService).insertTimeTable(timeTableDTO);
    }
}
