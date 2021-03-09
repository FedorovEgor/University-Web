package com.fedorov.spring.service.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

public class ScheduleDTO {
    private int scheduleId;
    private GroupDTO groupDTO;
    private CourseDTO courseDTO;
    private TeacherDTO teacherDTO;
    private TimeTableDTO timeTableDTO;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please pick a date.")
    private Date date;

    public ScheduleDTO() {
    }

    public ScheduleDTO(int scheduleId, Date date) {
        this.scheduleId = scheduleId;
        this.date = date;
    }

    public ScheduleDTO(GroupDTO groupDTO, CourseDTO courseDTO, TeacherDTO teacherDTO, TimeTableDTO timeTableDTO, Date date) {
        this.groupDTO = groupDTO;
        this.courseDTO = courseDTO;
        this.teacherDTO = teacherDTO;
        this.timeTableDTO = timeTableDTO;
        this.date = date;
    }

    public ScheduleDTO(int scheduleId, GroupDTO groupDTO, CourseDTO courseDTO, TeacherDTO teacherDTO, TimeTableDTO timeTableDTO, Date date) {
        this.scheduleId = scheduleId;
        this.groupDTO = groupDTO;
        this.courseDTO = courseDTO;
        this.teacherDTO = teacherDTO;
        this.timeTableDTO = timeTableDTO;
        this.date = date;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public GroupDTO getGroupDTO() {
        return groupDTO;
    }

    public void setGroupDTO(GroupDTO groupDTO) {
        if (groupDTO == null) {
            this.groupDTO = new GroupDTO();
            this.groupDTO.setGroupName("No group assigned.");
        }
        else
            this.groupDTO = groupDTO;
    }

    public CourseDTO getCourseDTO() {
        return courseDTO;
    }

    public void setCourseDTO(CourseDTO courseDTO) {
        if (courseDTO == null) {
            this.courseDTO = new CourseDTO();
            this.courseDTO.setCourseName("No course assigned.");
        }
        else
            this.courseDTO = courseDTO;
    }

    public TeacherDTO getTeacherDTO() {
        return teacherDTO;
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

    public TimeTableDTO getTimeTableDTO() {
        return timeTableDTO;
    }

    public void setTimeTableDTO(TimeTableDTO timeTableDTO) {
        if (timeTableDTO == null) {
            this.timeTableDTO = new TimeTableDTO();
            this.timeTableDTO.setTableName("No timetable assigned.");
        }
        else
            this.timeTableDTO = timeTableDTO;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDTO that = (ScheduleDTO) o;
        return scheduleId == that.scheduleId &&
                Objects.equals(groupDTO, that.groupDTO) &&
                Objects.equals(courseDTO, that.courseDTO) &&
                Objects.equals(teacherDTO, that.teacherDTO) &&
                Objects.equals(timeTableDTO, that.timeTableDTO) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, groupDTO, courseDTO, teacherDTO, timeTableDTO, date);
    }

    @Override
    public String toString() {
        return "ScheduleDTO{" +
                "scheduleId=" + scheduleId +
                ", groupDTO=" + groupDTO +
                ", courseDTO=" + courseDTO +
                ", teacherDTO=" + teacherDTO +
                ", timeTableDTO=" + timeTableDTO +
                ", date=" + date +
                '}';
    }
}

