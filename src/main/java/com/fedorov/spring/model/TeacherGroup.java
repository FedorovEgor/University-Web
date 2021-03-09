package com.fedorov.spring.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "teachers_groups")
public class TeacherGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "teacher_id")
    private int teacher_id;
    @Column(name = "group_id")
    private int group_id;

    public TeacherGroup() {
    }

    public TeacherGroup(int teacher_id, int group_id) {
        this.teacher_id = teacher_id;
        this.group_id = group_id;
    }

    public TeacherGroup(int id, int teacher_id, int group_id) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherGroup that = (TeacherGroup) o;
        return id == that.id &&
                teacher_id == that.teacher_id &&
                group_id == that.group_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teacher_id, group_id);
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
