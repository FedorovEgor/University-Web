package com.fedorov.spring.service;

import com.fedorov.spring.model.*;
import com.fedorov.spring.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityManagement {

    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TimeTableService timeTableService;

    public StudentDTO getStudent(int studentId) {
        return studentService.selectStudent(studentId);
    }

    public List<StudentDTO> getAllStudents() {
        return studentService.selectAllStudents();
    }

    public void addStudent(StudentDTO studentDTO) {
        studentService.insertStudent(studentDTO);
    }

    public boolean expelStudent(int studentId) {
        return studentService.deleteStudent(studentId);
    }

    public boolean updateStudent(StudentDTO studentDTO) {
        return studentService.updateStudent(studentDTO);
    }

    public CourseDTO getCourse(int courseId) {
        return courseService.selectCourse(courseId);
    }

    public List<CourseDTO> getAllCourses() {
        return courseService.selectAllCourses();
    }

    public void addCourse(CourseDTO courseDTO) {
        courseService.insertCourse(courseDTO);
    }

    public boolean deleteCourse(int courseId) {
        return courseService.deleteCourse(courseId);
    }

    public boolean updateCourse(CourseDTO courseDTO) {
        return courseService.updateCourse(courseDTO);
    }

    public GroupDTO getGroup(int groupId) {
        return groupService.selectGroup(groupId);
    }

    public List<GroupDTO> getAllGroups() {
        return groupService.selectAllGroups();
    }

    public void addGroup(GroupDTO groupDTO) {
        groupService.insertGroup(groupDTO);
    }

    public boolean disbandGroup(int groupId) {
        return groupService.deleteGroup(groupId);
    }

    public boolean updateGroup(GroupDTO groupDTO) {
        return groupService.updateGroup(groupDTO);
    }

    public TeacherDTO getTeacher(int teacherId) {
        return teacherService.selectTeacher(teacherId);
    }

    public List<TeacherDTO> getAllTeachers() {
        return teacherService.selectAllTeachers();
    }

    public void addTeacher(TeacherDTO teacherDTO) {
        teacherService.insertTeacher(teacherDTO);
    }

    public boolean retireTeacher(int teacherId) {
        return teacherService.deleteTeacher(teacherId);
    }

    public boolean updateTeacher(TeacherDTO teacherDTO) {
        return teacherService.updateTeacher(teacherDTO);
    }

    public TimeTableDTO getTimeTable(int timeTableId) {
        return timeTableService.selectTimeTable(timeTableId);
    }

    public List<TimeTableDTO> getAllTimeTables() {
        return timeTableService.selectAllTimeTables();
    }

    public void createTimeTable(TimeTableDTO timeTableDTO) {
        timeTableService.insertTimeTable(timeTableDTO);
    }

    public boolean deleteTimeTable(int timeTableId) {
        return timeTableService.deleteTimeTable(timeTableId);
    }

    public boolean updateTimeTable(TimeTableDTO timeTableDTO) {
        return timeTableService.updateTimeTable(timeTableDTO);
    }
}

