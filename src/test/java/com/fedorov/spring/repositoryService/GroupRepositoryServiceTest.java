package com.fedorov.spring.repositoryService;

import com.fedorov.spring.model.Group;
import com.fedorov.spring.repository.GroupRepository;
import com.fedorov.spring.service.GroupService;
import com.fedorov.spring.service.dto.GroupDTO;
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
public class GroupRepositoryServiceTest {

    @Mock
    GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    @Test
    void getAllGroups_listOfAllGroupsExpected() {
        List<Group> expectedGroups = Arrays.asList(
                new Group(1, "ed-48"),
                new Group(2, "ef-75")
        );

        when(groupRepository.findAll()).thenReturn(expectedGroups);
        List<GroupDTO> actualGroups = groupService.selectAllGroups();
        String actualGroupsElementClass = actualGroups.get(0).getClass().getSimpleName();

        assertEquals("GroupDTO", actualGroupsElementClass);
        verify(groupRepository).findAll();
    }

    @Test
    void getGroupById_singleGroupIsExpected() {
        Group expectedGroup = new Group(1, "ed-48");

        when(groupRepository.getOne(1)).thenReturn(expectedGroup);
        GroupDTO actualGroup = groupService.selectGroup(1);
        String actualGroupClass = actualGroup.getClass().getSimpleName();

        assertEquals("GroupDTO", actualGroupClass);
        verify(groupRepository).getOne(1);
    }

    @Test
    void deleteGroupById_groupExpectedToBeDeleted() {
        boolean isDeleted;

        isDeleted = groupService.deleteGroup(1);

        assertTrue(isDeleted);
        verify(groupRepository).deleteById(1);
    }

    @Test
    void updateGroup_groupExpectedToBeUpdated() {
        boolean isUpdated;

        Group group = new Group(1, "kf-55");
        GroupDTO groupDTO = new GroupDTO(1, "kf-55");
        isUpdated = groupService.updateGroup(groupDTO);

        assertTrue(isUpdated);
        verify(groupRepository).save(group);
    }

    @Test
    void insertGroup_newGroupExpectedToBeCreatedInDataBase() {
        Group group = new Group(1, "i-401");
        GroupDTO groupDTO = new GroupDTO(1, "i-401");
        groupService.insertGroup(groupDTO);
        verify(groupRepository).save(group);
    }
}

