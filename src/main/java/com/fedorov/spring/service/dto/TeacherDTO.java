package com.fedorov.spring.service.dto;

import com.fedorov.spring.model.Person;

import java.util.Objects;

public class TeacherDTO extends Person {
    private Integer teacherId;
    private String fullName = getFullName();

    public TeacherDTO() {

    }

    public TeacherDTO(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public TeacherDTO(Integer teacherId, String firstName, String lastName) {
        super(firstName, lastName);
        this.teacherId = teacherId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId + ", " + super.toString();
    }
}

