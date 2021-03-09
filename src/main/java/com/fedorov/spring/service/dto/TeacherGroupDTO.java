package com.fedorov.spring.service.dto;

import java.util.Objects;

public class TeacherGroupDTO {
    private int id;
    private int teacher_id;
    private int group_id;

    public TeacherGroupDTO() {
    }

    public TeacherGroupDTO(int teacher_id, int group_id) {
        this.teacher_id = teacher_id;
        this.group_id = group_id;
    }

    public TeacherGroupDTO(int id, int teacher_id, int group_id) {
        this.id = id;
        this.teacher_id = teacher_id;
        this.group_id = group_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    @Override
    public String toString() {
        return "TeacherGroup{" +
                "teacherGroupId=" + id +
                ", teacher_id=" + teacher_id +
                ", group_id=" + group_id +
                '}';
    }
}

