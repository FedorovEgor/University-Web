package com.fedorov.spring.service;

import com.fedorov.spring.mappers.GroupMapper;
import com.fedorov.spring.mappers.TeacherGroupMapper;
import com.fedorov.spring.mappers.TeacherMapper;
import com.fedorov.spring.model.Group;
import com.fedorov.spring.model.Teacher;
import com.fedorov.spring.model.TeacherGroup;
import com.fedorov.spring.repository.TeacherGroupRepository;
import com.fedorov.spring.service.dto.GroupDTO;
import com.fedorov.spring.service.dto.TeacherDTO;
import com.fedorov.spring.service.dto.TeacherGroupDTO;
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
public class TeacherGroupService {

    private final TeacherGroupRepository teacherGroupRepository;

    @Autowired
    public TeacherGroupService(TeacherGroupRepository teacherGroupRepository) {
        this.teacherGroupRepository = teacherGroupRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherGroupService.class);

    public void insertTeacherGroup(TeacherGroupDTO teacherGroupDTO) {
        LOGGER.debug("Creating teacher_group '{}'", teacherGroupDTO);
        try {
            teacherGroupRepository.save(TeacherGroupMapper.INSTANCE.teacherGroupDTOtoEntity(teacherGroupDTO));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create teacher_group '{}'", teacherGroupDTO);
            throw new DatabaseUpdateException("Teacher_group was not created", e);
        }
        LOGGER.debug("Teacher_group was created");
    }

    public boolean updateTeacherGroup(TeacherGroupDTO teacherGroupDTO) {
        boolean isUpdated = false;
        LOGGER.debug("Updating teacher_group '{}'", teacherGroupDTO);
        try {
            teacherGroupDTO.setId(teacherGroupDTO.getId());
            teacherGroupDTO.setTeacher_id(teacherGroupDTO.getTeacher_id());
            teacherGroupDTO.setGroup_id(teacherGroupDTO.getGroup_id());

            teacherGroupRepository.save(TeacherGroupMapper.INSTANCE.teacherGroupDTOtoEntity(teacherGroupDTO));
            isUpdated = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to update teacher_group '{}'", teacherGroupDTO);
            throw new DatabaseUpdateException("Teacher_group was not updated", e);
        }
        LOGGER.debug("Teacher_group was updated - {}", isUpdated);
        return isUpdated;
    }

    public boolean deleteTeacherGroup(int id) {
        boolean isDeleted = false;
        LOGGER.debug("Deleting teacher_group by teacherGroupId '{}'", id);
        try {
            teacherGroupRepository.deleteById(id);
            isDeleted = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete teacher_group with teacherGroupId '{}'", id);
            throw new DatabaseUpdateException("Teacher_group was not deleted", e);
        }
        LOGGER.debug("Teacher_group was deleted - {}", isDeleted);
        return isDeleted;
    }

    public TeacherGroupDTO selectTeacherGroup(int id) {
        TeacherGroupDTO teacherGroupDTO;
        LOGGER.debug("Retrieving teacher_group by teacherGroupId '{}'", id);
        try {
            teacherGroupDTO = TeacherGroupMapper.INSTANCE.teacherGroupToDTO(teacherGroupRepository.getOne(id));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve teacher_group with teacherGroupId '{}'", id);
            throw new EntityNotFoundException("Teacher_group was not retrieved", e);
        }
        LOGGER.debug("Teacher_group was retrieved");
        return teacherGroupDTO;
    }

    public TeacherGroupDTO selectTeacherGroup(int teacherId, int groupId) {
        TeacherGroupDTO teacherGroupDTO;
        LOGGER.debug("Retrieving teacher_group by teacherId '{}' and groupId '{}'", teacherId, groupId);
        try {
            teacherGroupDTO = TeacherGroupMapper.INSTANCE.teacherGroupToDTO(teacherGroupRepository.findByTeacherIdAndGroupId(teacherId, groupId));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve teacher_group with teacherId '{}' and groupId '{}'", teacherId, groupId);
            throw new EntityNotFoundException("Teacher_group was not retrieved", e);
        }
        LOGGER.debug("Teacher_group was retrieved");
        return teacherGroupDTO;
    }

    public List<TeacherGroupDTO> selectAllTeachersGroups() {
        List<TeacherGroupDTO> teacherGroups;
        LOGGER.debug("Retrieving all teachers_groups");
        try {
            teacherGroups = teacherGroupRepository.findAll().stream().map(TeacherGroupMapper.INSTANCE::teacherGroupToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve teachers_groups");
            throw new EntityNotFoundException("Teachers_groups were not retrieved", e);
        }
        LOGGER.debug("Teachers_groups were retrieved");
        return teacherGroups;
    }

    public void addTeacherToGroup(TeacherDTO teacherDTO, GroupDTO groupDTO) {
        Teacher teacher = TeacherMapper.INSTANCE.teacherDTOtoEntity(teacherDTO);
        Group group = GroupMapper.INSTANCE.groupDTOtoEntity(groupDTO);
        TeacherGroup teacherGroup = new TeacherGroup(teacher.getTeacherId(), group.getGroupId());
        LOGGER.debug("Adding new teacher_group '{}'", teacherGroup);
        try {
            teacherGroupRepository.save(teacherGroup);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to add new teacher_group '{}'", teacherGroup);
            throw new DatabaseUpdateException("Teacher_group was not added", e);
        }
        LOGGER.debug("Teacher_group was added");
    }

    public void deleteTeacherFromGroup(TeacherDTO teacherDTO, GroupDTO groupDTO) {
        Teacher teacher = TeacherMapper.INSTANCE.teacherDTOtoEntity(teacherDTO);
        Group group = GroupMapper.INSTANCE.groupDTOtoEntity(groupDTO);
        TeacherGroup teacherGroup = teacherGroupRepository.findByTeacherIdAndGroupId(teacher.getTeacherId(), group.getGroupId());
        LOGGER.debug("Deleting teacher_group '{}'", teacherGroup);
        try {
            teacherGroupRepository.deleteById(teacherGroup.getId());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete teacher_group '{}'", teacherGroup);
            throw new DatabaseUpdateException("Teacher group was not deleted", e);
        }
        LOGGER.debug("Teacher_group was deleted");
    }
}

