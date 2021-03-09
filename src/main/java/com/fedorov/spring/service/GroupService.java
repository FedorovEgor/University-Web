package com.fedorov.spring.service;

import com.fedorov.spring.mappers.GroupMapper;
import com.fedorov.spring.repository.GroupRepository;
import com.fedorov.spring.service.dto.GroupDTO;
import com.fedorov.spring.service.exceptions.DatabaseUpdateException;
import com.fedorov.spring.service.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);

    public void insertGroup(GroupDTO groupDTO) {
        LOGGER.debug("Creating group '{}'", groupDTO);
        try {
            groupRepository.save(GroupMapper.INSTANCE.groupDTOtoEntity(groupDTO));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create group '{}'", groupDTO);
            throw new DatabaseUpdateException("Group was not created", e);
        }
        LOGGER.debug("Group was created");
    }

    public boolean updateGroup(GroupDTO groupDTO) {
        boolean isUpdated = false;
        LOGGER.debug("Updating group '{}'", groupDTO);
        try {
            groupDTO.setGroupId(groupDTO.getGroupId());
            groupDTO.setGroupName(groupDTO.getGroupName());

            groupRepository.save(GroupMapper.INSTANCE.groupDTOtoEntity(groupDTO));
            isUpdated = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to update group '{}'", groupDTO);
            throw new DatabaseUpdateException("Group was not updated", e);
        }
        LOGGER.debug("Group was updated - {}", isUpdated);
        return isUpdated;
    }

    public boolean deleteGroup(int id) {
        boolean isDeleted = false;
        LOGGER.debug("Deleting group by groupId '{}'", id);
        try {
            groupRepository.deleteById(id);
            isDeleted = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete group with groupId '{}'", id);
            throw new DatabaseUpdateException("Group was not deleted", e);
        }
        LOGGER.debug("Group was deleted - {}", isDeleted);
        return isDeleted;
    }

    public GroupDTO selectGroup(int id) {
        GroupDTO groupDTO;
        LOGGER.debug("Retrieving group by groupId '{}'", id);
        try {
            groupDTO = GroupMapper.INSTANCE.groupToDTO(groupRepository.getOne(id));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve group with groupId '{}'", id);
            throw new EntityNotFoundException("Group was not retrieved", e);
        }
        LOGGER.debug("Group was retrieved");
        return groupDTO;
    }

    public List<GroupDTO> selectAllGroups() {
        List<GroupDTO> groups;
        LOGGER.debug("Retrieving all groups");
        try {
            groups = groupRepository.findAll().stream().map(GroupMapper.INSTANCE::groupToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve groups");
            throw new EntityNotFoundException("Groups were not retrieved", e);
        }
        LOGGER.debug("Groups were retrieved");
        return groups;
    }
}

