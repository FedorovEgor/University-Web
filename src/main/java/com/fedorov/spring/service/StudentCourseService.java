package com.fedorov.spring.service;

import com.fedorov.spring.dao.DAOException;
import com.fedorov.spring.mappers.CourseMapper;
import com.fedorov.spring.mappers.StudentCourseMapper;
import com.fedorov.spring.mappers.StudentMapper;
import com.fedorov.spring.model.Course;
import com.fedorov.spring.model.Student;
import com.fedorov.spring.model.StudentCourse;
import com.fedorov.spring.repository.StudentCourseRepository;
import com.fedorov.spring.service.dto.CourseDTO;
import com.fedorov.spring.service.dto.StudentCourseDTO;
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
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;

    @Autowired
    public StudentCourseService(StudentCourseRepository studentCourseRepository) {
        this.studentCourseRepository = studentCourseRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCourseService.class);

    public void insertStudentCourse(StudentCourseDTO studentCourseDTO) {
        LOGGER.debug("Creating student_course '{}'", studentCourseDTO);
        try {
            studentCourseRepository.save(StudentCourseMapper.INSTANCE.studentCourseDTOtoEntity(studentCourseDTO));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create student_course '{}'", studentCourseDTO);
            throw new DatabaseUpdateException("Student_course was not created");
        }
        LOGGER.debug("Student_course was created");
    }

    public boolean updateStudentCourse(StudentCourseDTO studentCourseDTO) {
        boolean isUpdated = false;
        LOGGER.debug("Updating student_course '{}'", studentCourseDTO);
        try {
            studentCourseDTO.setId(studentCourseDTO.getId());
            studentCourseDTO.setStudent_id(studentCourseDTO.getStudent_id());
            studentCourseDTO.setCourse_id(studentCourseDTO.getCourse_id());

            studentCourseRepository.save(StudentCourseMapper.INSTANCE.studentCourseDTOtoEntity(studentCourseDTO));
            isUpdated = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to update student_course '{}'", studentCourseDTO);
            throw new DatabaseUpdateException("Student_course was not updated", e);
        }
        LOGGER.debug("Student_course was updated - {}", isUpdated);
        return isUpdated;
    }

    public boolean deleteStudentCourse(int id) {
        boolean isDeleted = false;
        LOGGER.debug("Deleting student_course by studentCourseId '{}'", id);
        try {
            studentCourseRepository.deleteById(id);
            isDeleted = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete student_course with studentCourseId '{}'", id);
            throw new DatabaseUpdateException("Student_course was not deleted", e);
        }
        LOGGER.debug("Student_course was deleted - {}", isDeleted);
        return isDeleted;
    }

    public StudentCourseDTO selectStudentCourse(int id) {
        StudentCourseDTO studentCourseDTO;
        LOGGER.debug("Retrieving student_course by studentCourseId '{}'", id);
        try {
            studentCourseDTO = StudentCourseMapper.INSTANCE.studentCourseToDTO(studentCourseRepository.getOne(id));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve student_course with studentCourseId '{}'", id);
            throw new EntityNotFoundException("Student_course was not retrieved", e);
        }
        LOGGER.debug("Student_course was retrieved");
        return studentCourseDTO;
    }

    public StudentCourseDTO selectStudentCourse(int studentId, int courseId) {
        StudentCourseDTO studentCourseDTO;
        LOGGER.debug("Retrieving student_course by studentId '{}' and courseId '{}'", studentId, courseId);
        try {
            studentCourseDTO = StudentCourseMapper.INSTANCE.studentCourseToDTO(studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve student_course with studentId '{}' and courseId '{}'", studentId, courseId);
            throw new EntityNotFoundException("Student_course was not retrieved", e);
        }
        LOGGER.debug("Student_course was retrieved");
        return studentCourseDTO;
    }

    public List<StudentCourseDTO> selectAllStudentsCourses() {
        List<StudentCourseDTO> studentCourses;
        LOGGER.debug("Retrieving all students_courses");
        try {
            studentCourses = studentCourseRepository.findAll().stream().map(StudentCourseMapper.INSTANCE::studentCourseToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve students_courses");
            throw new EntityNotFoundException("Students_courses were not retrieved", e);
        }
        LOGGER.debug("Students_courses were retrieved");
        return studentCourses;
    }

    public void addStudentToCourse(StudentDTO studentDTO, CourseDTO courseDTO) {
        Student student = StudentMapper.INSTANCE.studentDTOtoEntity(studentDTO);
        Course course = CourseMapper.INSTANCE.courseDTOtoEntity(courseDTO);
        StudentCourse studentCourse = new StudentCourse(student.getStudentId(), course.getCourseId());
        LOGGER.debug("Adding new student_course '{}'", studentCourse);
        try {
            studentCourseRepository.save(studentCourse);
        } catch (DAOException e) {
            LOGGER.error("Failed to add student_course '{}'", studentCourse);
            throw new DatabaseUpdateException("Student_course was not added", e);
        }
        LOGGER.debug("Student_course was added");
    }

    public void deleteStudentFromCourse(StudentDTO studentDTO, CourseDTO courseDTO) {
        Student student = StudentMapper.INSTANCE.studentDTOtoEntity(studentDTO);
        Course course = CourseMapper.INSTANCE.courseDTOtoEntity(courseDTO);
        StudentCourse studentCourse = studentCourseRepository.findByStudentIdAndCourseId(student.getStudentId(), course.getCourseId());
        LOGGER.debug("Deleting student_course '{}'", studentCourse);
        try {
            studentCourseRepository.deleteById(studentCourse.getId());
        } catch (DAOException e) {
            LOGGER.error("Failed to delete student_course '{}'", studentCourse);
            throw new DatabaseUpdateException("Student_course was not deleted", e);
        }
        LOGGER.debug("Student_course was deleted");
    }
}


