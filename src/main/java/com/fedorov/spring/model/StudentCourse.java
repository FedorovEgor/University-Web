package com.fedorov.spring.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "students_courses")
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "student_id")
    private int student_id;
    @Column(name = "course_id")
    private int course_id;

    public StudentCourse() {
    }

    public StudentCourse(int student_id, int course_id) {
        this.student_id = student_id;
        this.course_id = course_id;
    }

    public StudentCourse(int id, int student_id, int course_id) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourse that = (StudentCourse) o;
        return id == that.id &&
                student_id == that.student_id &&
                course_id == that.course_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student_id, course_id);
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

