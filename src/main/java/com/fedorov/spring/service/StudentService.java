package com.fedorov.spring.service;

import com.fedorov.spring.mappers.StudentMapper;
import com.fedorov.spring.model.Student;
import com.fedorov.spring.repository.StudentRepository;
import com.fedorov.spring.service.dto.StudentDTO;
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
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    public void insertStudent(StudentDTO studentDTO) {
        LOGGER.debug("Creating student '{}'", studentDTO);
        try {
            studentRepository.save(studentInsertHelper(studentDTO));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create student '{}'", studentDTO);
            throw new DatabaseUpdateException("Student was not created", e);
        }
        LOGGER.debug("Student was created");
    }

    public boolean updateStudent(StudentDTO studentDTO) {
        boolean isUpdated = false;
        LOGGER.debug("Updating student '{}'", studentDTO);
        try {
            studentDTO.setStudentId(studentDTO.getStudentId());
            studentDTO.setGroupDTO(studentDTO.getGroupDTO());
            studentDTO.setFirstName(studentDTO.getFirstName());
            studentDTO.setLastName(studentDTO.getLastName());

            studentRepository.save(studentInsertHelper(studentDTO));
            isUpdated = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to update student '{}'", studentDTO);
            throw new DatabaseUpdateException("Student was not updated", e);
        }
        LOGGER.debug("Student was updated - {}", isUpdated);
        return isUpdated;
    }

    public boolean deleteStudent(int id) {
        boolean isDeleted = false;
        LOGGER.debug("Deleting student by studentId '{}'", id);
        try {
            studentRepository.deleteById(id);
            isDeleted = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete student with studentId '{}'", id);
            throw new DatabaseUpdateException("Student was not deleted", e);
        }
        LOGGER.debug("Student was deleted - {}", isDeleted);
        return isDeleted;
    }

    public StudentDTO selectStudent(int id) {
        StudentDTO studentDTO;
        LOGGER.debug("Retrieving student by studentId '{}'", id);
        try {
            studentDTO = StudentMapper.INSTANCE.studentToDTO(studentRepository.getOne(id));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve student with studentId '{}'", id);
            throw new EntityNotFoundException("Student was not retrieved", e);
        }
        LOGGER.debug("Student was retrieved");
        return studentDTO;
    }

    public List<StudentDTO> selectAllStudents() {
        List<StudentDTO> students;
        LOGGER.debug("Retrieving all students");
        try {
            students = studentRepository.findAll().stream().map(StudentMapper.INSTANCE::studentToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve students");
            throw new EntityNotFoundException("Students were not retrieved", e);
        }
        LOGGER.debug("Students were retrieved");
        return students;
    }

    private Student studentInsertHelper(StudentDTO studentDTO) {
        Student student = StudentMapper.INSTANCE.studentDTOtoEntity(studentDTO);
        if (student.getGroup().getGroupId() == 0)
            student.setGroup(null);
        return student;
    }
}

