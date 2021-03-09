package com.fedorov.spring.service.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class CourseDTO {
    private Integer courseId;
    private TeacherDTO teacherDTO;
    @NotEmpty(message = "Course name should not be empty.")
    @Size(min = 2, max = 100, message = "Course name should be between 2 and 100 symbols.")
    private String courseName;
    private List<ScheduleDTO> schedulesDTO;

    public CourseDTO() {
    }

    public CourseDTO(String courseName) {
        this.courseName = courseName;
    }

    public CourseDTO(Integer courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public CourseDTO(TeacherDTO teacherDTO, String courseName) {
        this.teacherDTO = teacherDTO;
        this.courseName = courseName;
    }

    public CourseDTO(Integer courseId, TeacherDTO teacherDTO, String courseName) {
        this.courseId = courseId;
        this.teacherDTO = teacherDTO;
        this.courseName = courseName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public TeacherDTO getTeacherDTO() {
        return teacherDTO;
    }

    public List<ScheduleDTO> getSchedulesDTO() {
        return schedulesDTO;
    }

    public void setSchedulesDTO(List<ScheduleDTO> schedulesDTO) {
        this.schedulesDTO = schedulesDTO;
    }

    public void setTeacherDTO(TeacherDTO teacherDTO) {
        if (teacherDTO == null) {
            this.teacherDTO = new TeacherDTO();
            this.teacherDTO.setFirstName("No teacher assigned.");
            this.teacherDTO.setLastName("");
        }
        else
            this.teacherDTO = teacherDTO;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return courseId == courseDTO.courseId &&
                Objects.equals(teacherDTO, courseDTO.teacherDTO) &&
                Objects.equals(courseName, courseDTO.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, teacherDTO, courseName);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", teacherDTO=" + teacherDTO +
                '}';
    }
}

