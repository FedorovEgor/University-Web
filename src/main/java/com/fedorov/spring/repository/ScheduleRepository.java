package com.fedorov.spring.repository;

import com.fedorov.spring.model.Schedule;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Profile("default")
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    List<Schedule> findAllByDate(Date date);

    @Modifying
    @Query("select s from Schedule s where s.group.groupId = ?1 and s.date = ?2")
    List<Schedule> findAllByGroupIdAndDate(int groupId, Date date);

    @Modifying
    @Query("select s from Schedule s where s.group.groupId = ?1 and MONTH(s.date) = ?2")
    List<Schedule> findAllByGroupIdAndMonth(int groupId, int monthNo);

    @Modifying
    @Query("select s from Schedule s where s.teacher.teacherId = ?1 and s.date = ?2")
    List<Schedule> findAllByTeacherIdAndDate(int teacherId, Date date);

    @Modifying
    @Query("select s from Schedule s where s.teacher.teacherId = ?1 and MONTH(s.date) = ?2")
    List<Schedule> findAllByTeacherIdAndMonth(int teacherId, int monthNo);
}
