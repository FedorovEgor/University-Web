package com.fedorov.spring.service.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

public class TimeTableDTO {
    private Integer id;
    @NotEmpty(message = "Timetable name should not be empty.")
    @Size(min = 2, max = 25, message = "Timetable name should be between 2 and 25 symbols.")
    private String tableName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please pick a date.")
    private Date date;

    public TimeTableDTO() {
    }

    public TimeTableDTO(String tableName) {
        this.tableName = tableName;
    }

    public TimeTableDTO(Integer id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }

    public TimeTableDTO(Integer id, String tableName, Date date) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeTableDTO that = (TimeTableDTO) o;
        return id == that.id &&
                Objects.equals(tableName, that.tableName) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tableName, date);
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "id=" + id +
                ", name='" + tableName + '\'' + ", date='" + date + '\'' +
                '}';
    }
}

