package com.fedorov.spring.repository;

import com.fedorov.spring.model.TeacherGroup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class TeacherGroupRepositoryTest {

    @Autowired
    private TeacherGroupRepository teacherGroupRepository;

    @Test
    void getTeacherGroupsByTeacherIdAndGroupId_singleTeacherGroupIsExpected() {
        TeacherGroup expectedTeacherGroup = new TeacherGroup(2,2,2);
        TeacherGroup actualTeacherGroup = teacherGroupRepository.findByTeacherIdAndGroupId(2,2);

        assertEquals(expectedTeacherGroup, actualTeacherGroup);
    }
}
