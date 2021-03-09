package com.fedorov.spring.service.dto;

import java.util.Objects;

public class StudentCourseDTO {
    private int id;
    private int student_id;
    private int course_id;

    public StudentCourseDTO() {
    }

    public StudentCourseDTO(int student_id, int course_id) {
        this.student_id = student_id;
        this.course_id = course_id;
    }

    public StudentCourseDTO(int id, int student_id, int course_id) {
        this.id = id;
        this.student_id = student_id;
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    @Override
    public String toString() {
        return "StudentCourse{" +
                "studentCourseId=" + id +
                ", student_id=" + student_id +
                ", course_id=" + course_id +
                '}';
    }
}

