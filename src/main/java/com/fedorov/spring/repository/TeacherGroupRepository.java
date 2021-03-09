package com.fedorov.spring.repository;

import com.fedorov.spring.model.TeacherGroup;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Profile("default")
public interface TeacherGroupRepository extends JpaRepository<TeacherGroup, Integer> {

    @Query("select tg from TeacherGroup tg where tg.teacher_id = ?1 and tg.group_id = ?2")
    TeacherGroup findByTeacherIdAndGroupId(int teacher_id, int group_id);
}
