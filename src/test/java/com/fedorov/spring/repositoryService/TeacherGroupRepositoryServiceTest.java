package com.fedorov.spring.repositoryService;

import com.fedorov.spring.model.TeacherGroup;
import com.fedorov.spring.repository.TeacherGroupRepository;
import com.fedorov.spring.service.TeacherGroupService;
import com.fedorov.spring.service.dto.GroupDTO;
import com.fedorov.spring.service.dto.TeacherDTO;
import com.fedorov.spring.service.dto.TeacherGroupDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeacherGroupRepositoryServiceTest {

    @Mock
    TeacherGroupRepository teacherGroupRepository;

    @InjectMocks
    private TeacherGroupService teacherGroupService;

    @Test
    void getAllTeachersGroups_listOfAllTeachersGroupsIsExpected() {
        List<TeacherGroup> expectedTeachersGroups = Arrays.asList(
                new TeacherGroup(5,7),
                new TeacherGroup(8,1)
        );

        when(teacherGroupRepository.findAll()).thenReturn(expectedTeachersGroups);
        List<TeacherGroupDTO> actualTeachersGroups = teacherGroupService.selectAllTeachersGroups();
        String actualTeachersGroupsElementsClass = actualTeachersGroups.get(0).getClass().getSimpleName();

        assertEquals("TeacherGroupDTO", actualTeachersGroupsElementsClass);
        verify(teacherGroupRepository).findAll();
    }

    @Test
    void getTeacherGroupById_singleTeacherGroupIsExpected() {
        TeacherGroup expectedTeacherGroup = new TeacherGroup(1,3,7);

        when(teacherGroupRepository.getOne(1)).thenReturn(expectedTeacherGroup);
        TeacherGroupDTO actualTeacherGroup = teacherGroupService.selectTeacherGroup(1);
        String actualTeacherGroupClass = actualTeacherGroup.getClass().getSimpleName();

        assertEquals("TeacherGroupDTO", actualTeacherGroupClass);
        verify(teacherGroupRepository).getOne(1);
    }

    @Test
    void getTeacherGroupByTeacherIdAndGroupId_singleTeacherGroupIsExpected() {
        TeacherGroup expectedTeacherGroup = new TeacherGroup(6,4);

        when(teacherGroupRepository.findByTeacherIdAndGroupId(6,4)).thenReturn(expectedTeacherGroup);
        TeacherGroupDTO actualTeacherGroup = teacherGroupService.selectTeacherGroup(6,4);
        String actualTeacherGroupClass = actualTeacherGroup.getClass().getSimpleName();

        assertEquals("TeacherGroupDTO", actualTeacherGroupClass);
        verify(teacherGroupRepository).findByTeacherIdAndGroupId(6,4);
    }

    @Test
    void deleteTeacherGroup_teacherGroupIsExpectedToBeDeleted() {
        boolean isDeleted;

        isDeleted = teacherGroupService.deleteTeacherGroup(6);

        assertTrue(isDeleted);
        verify(teacherGroupRepository).deleteById(6);
    }

    @Test
    void updateTeacherGroup_teacherGroupIsExpectedToBeUpdated() {
        boolean isUpdated;

        TeacherGroup teacherGroup = new TeacherGroup(7,5);
        TeacherGroupDTO teacherGroupDTO = new TeacherGroupDTO(7,5);
        isUpdated = teacherGroupService.updateTeacherGroup(teacherGroupDTO);

        assertTrue(isUpdated);
        verify(teacherGroupRepository).save(teacherGroup);
    }

    @Test
    void insertTeacherGroup_teacherGroupIsExpectedToBeCreatedInDataBase() {
        TeacherGroup teacherGroup = new TeacherGroup(3,3);
        TeacherGroupDTO teacherGroupDTO = new TeacherGroupDTO(3,3);
        teacherGroupService.insertTeacherGroup(teacherGroupDTO);
        verify(teacherGroupRepository).save(teacherGroup);
    }

    @Test
    void addTeacherToGroup_teacherShouldBeAddedToSpecificGroup() {
        TeacherDTO teacherDTO = new TeacherDTO(1, "Bob", "Coul");
        GroupDTO groupDTO = new GroupDTO(1, "ed-57");
        TeacherGroup teacherGroup = new TeacherGroup(teacherDTO.getTeacherId(), groupDTO.getGroupId());
        teacherGroupService.addTeacherToGroup(teacherDTO, groupDTO);

        verify(teacherGroupRepository).save(teacherGroup);
    }

    @Test
    void deleteTeacherFromGroup_teacherShouldBeDeletedFromSpecificGroup() {
        TeacherDTO teacherDTO = new TeacherDTO(6, "Bob", "Coul");
        GroupDTO groupDTO = new GroupDTO(1, "ed-57");
        TeacherGroup teacherGroup = new TeacherGroup(teacherDTO.getTeacherId(), groupDTO.getGroupId());

        when(teacherGroupRepository.findByTeacherIdAndGroupId(teacherDTO.getTeacherId(), groupDTO.getGroupId())).thenReturn(teacherGroup);
        teacherGroupService.deleteTeacherFromGroup(teacherDTO, groupDTO);
        verify(teacherGroupRepository).findByTeacherIdAndGroupId(teacherDTO.getTeacherId(), groupDTO.getGroupId());
        verify(teacherGroupRepository).deleteById(teacherGroup.getId());
    }
}

