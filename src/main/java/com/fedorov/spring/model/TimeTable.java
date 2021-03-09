package com.fedorov.spring.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "timetables", schema = "public")
public class TimeTable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "table_name")
    private String tableName;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @OneToMany(mappedBy = "timeTable")
    private List<Schedule> schedules;

    public TimeTable() {
    }

    public TimeTable(String tableName) {
        this.tableName = tableName;
    }

    public TimeTable(Integer id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }

    public TimeTable(String tableName, Date date) {
        this.tableName = tableName;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
    }

    public void removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeTable timeTable = (TimeTable) o;
        return id == timeTable.id &&
                Objects.equals(tableName, timeTable.tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tableName);
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "id=" + id +
                ", name='" + tableName + '\'' +
                '}';
    }
}


