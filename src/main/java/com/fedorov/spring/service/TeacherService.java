package com.fedorov.spring.service;

import com.fedorov.spring.mappers.TeacherMapper;
import com.fedorov.spring.repository.TeacherRepository;
import com.fedorov.spring.service.dto.TeacherDTO;
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
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherService.class);

    public void insertTeacher(TeacherDTO teacherDTO) {
        LOGGER.debug("Creating teacher '{}'", teacherDTO);
        try {
            teacherRepository.save(TeacherMapper.INSTANCE.teacherDTOtoEntity(teacherDTO));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create teacher '{}'", teacherDTO);
            throw new DatabaseUpdateException("Teacher was not created", e);
        }
        LOGGER.debug("Teacher was created");
    }

    public boolean updateTeacher(TeacherDTO teacherDTO) {
        boolean isUpdated = false;
        LOGGER.debug("Updating teacher '{}'", teacherDTO);
        try {
            teacherDTO.setTeacherId(teacherDTO.getTeacherId());
            teacherDTO.setFirstName(teacherDTO.getFirstName());
            teacherDTO.setLastName(teacherDTO.getLastName());

            teacherRepository.save(TeacherMapper.INSTANCE.teacherDTOtoEntity(teacherDTO));
            isUpdated = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to update teacher '{}'", teacherDTO);
            throw new DatabaseUpdateException("Teacher was not updated", e);
        }
        LOGGER.debug("Teacher was updated - {}", isUpdated);
        return isUpdated;
    }

    public boolean deleteTeacher(int id) {
        boolean isDeleted = false;
        LOGGER.debug("Deleting teacher by teacherId '{}'", id);
        try {
            teacherRepository.deleteById(id);
            isDeleted = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete teacher with teacherId '{}'", id);
            throw new DatabaseUpdateException("Teacher was not deleted", e);
        }
        LOGGER.debug("Teacher was deleted - {}", isDeleted);
        return isDeleted;
    }

    public TeacherDTO selectTeacher(int id) {
        TeacherDTO teacher;
        LOGGER.debug("Retrieving teacher by teacherId '{}'", id);
        try {
            teacher = TeacherMapper.INSTANCE.teacherToDTO(teacherRepository.getOne(id));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve teacher with teacherId '{}'", id);
            throw new EntityNotFoundException("Teacher was not retrieved", e);
        }
        LOGGER.debug("Teacher was retrieved");
        return teacher;
    }

    public List<TeacherDTO> selectAllTeachers() {
        List<TeacherDTO> teachers;
        LOGGER.debug("Retrieving all teachers");
        try {
            teachers = teacherRepository.findAll().stream().map(TeacherMapper.INSTANCE::teacherToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve teachers");
            throw new EntityNotFoundException("Teachers were not retrieved", e);
        }
        LOGGER.debug("Teachers were retrieved");
        return teachers;
    }
}

