package com.fedorov.spring.service.dto;

import com.fedorov.spring.model.Person;

import java.util.Objects;

public class StudentDTO extends Person {
    private int studentId;
    private GroupDTO groupDTO;

    public StudentDTO() {
    }

    public StudentDTO(GroupDTO groupDTO) {
        this.groupDTO = groupDTO;
    }

    public StudentDTO(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public StudentDTO(GroupDTO groupDTO, String firstName, String lastName) {
        super(firstName, lastName);
        this.groupDTO = groupDTO;
    }

    public StudentDTO(int studentId, String firstName, String lastName) {
        super(firstName, lastName);
        this.studentId = studentId;
    }

    public StudentDTO(int studentId, GroupDTO groupDTO, String firstName, String lastName) {
        super(firstName, lastName);
        this.studentId = studentId;
        this.groupDTO = groupDTO;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    @Override
    public String toString() {
        return "StudentDTO{" +
                "studentId=" + studentId +
                ", groupDTO=" + groupDTO +
                '}';
    }
}

/*

 */