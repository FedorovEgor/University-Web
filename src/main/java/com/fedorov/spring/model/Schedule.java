package com.fedorov.spring.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int scheduleId;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @ManyToOne
    @JoinColumn(name = "timetable_id")
    private TimeTable timeTable;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Schedule() {
    }

    public Schedule(Group group, Course course, Teacher teacher, TimeTable timeTable, Date date) {
        this.group = group;
        this.course = course;
        this.teacher = teacher;
        this.timeTable = timeTable;
        this.date = date;
    }

    public Schedule(int scheduleId, Group group, Course course, Teacher teacher, TimeTable timeTable, Date date) {
        this.scheduleId = scheduleId;
        this.group = group;
        this.course = course;
        this.teacher = teacher;
        this.timeTable = timeTable;
        this.date = date;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
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
        Schedule schedule = (Schedule) o;
        return scheduleId == schedule.scheduleId &&
                Objects.equals(group, schedule.group) &&
                Objects.equals(course, schedule.course) &&
                Objects.equals(teacher, schedule.teacher) &&
                Objects.equals(timeTable, schedule.timeTable) &&
                Objects.equals(date, schedule.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, group, course, teacher, timeTable, date);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", group=" + group +
                ", course=" + course +
                ", teacher=" + teacher +
                ", timeTable=" + timeTable +
                ", date=" + date +
                '}';
    }
}

