package com.fedorov.spring.service;

import com.fedorov.spring.mappers.CourseMapper;
import com.fedorov.spring.model.Course;
import com.fedorov.spring.repository.CourseRepository;
import com.fedorov.spring.service.dto.CourseDTO;
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
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    public void insertCourse(CourseDTO courseDTO) {
        LOGGER.debug("Creating course '{}'", courseDTO);
        try {
            courseRepository.save(courseInsertHelper(courseDTO));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create course '{}'", courseDTO);
            throw new DatabaseUpdateException("Course was not created", e);
        }
        LOGGER.debug("Course was created");
    }

    public boolean updateCourse(CourseDTO courseDTO) {
        boolean isUpdated = false;
        LOGGER.debug("Updating course '{}'", courseDTO);
        try {
            courseDTO.setCourseId(courseDTO.getCourseId());
            courseDTO.setTeacherDTO(courseDTO.getTeacherDTO());
            courseDTO.setCourseName(courseDTO.getCourseName());

            courseRepository.save(courseInsertHelper(courseDTO));
            isUpdated = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to update course '{}'", courseDTO);
            throw new DatabaseUpdateException("course was not updated", e);
        }
        LOGGER.debug("Course was updated - {}", isUpdated);
        return isUpdated;
    }

    public boolean deleteCourse(int id) {
        boolean isDeleted = false;
        LOGGER.debug("Deleting course by courseId '{}'", id);
        try {
            courseRepository.deleteById(id);
            isDeleted = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delele course with courseId '{}'", id);
            throw new DatabaseUpdateException("Course was not deleted", e);
        }
        LOGGER.debug("Course was deleted - {}", isDeleted);
        return isDeleted;
    }

    public CourseDTO selectCourse(int id) {
        CourseDTO courseDTO;
        LOGGER.debug("Retrieving course by courseId '{}'", id);
        try {
            courseDTO = CourseMapper.INSTANCE.courseToDTO(courseRepository.getOne(id));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve course with courseId '{}'", id);
            throw new EntityNotFoundException("Course was not retrieved", e);
        }
        LOGGER.debug("Course was retrieved");
        return courseDTO;
    }

    public List<CourseDTO> selectAllCourses() {
        List<CourseDTO> courses;
        LOGGER.debug("Retrieving all courses");
        try {
            courses = courseRepository.findAll().stream().map(CourseMapper.INSTANCE::courseToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve courses");
            throw new EntityNotFoundException("Courses were not retrieved", e);
        }
        LOGGER.debug("Courses were retrieved");
        return courses;
    }

    private Course courseInsertHelper(CourseDTO courseDTO) {
        Course course = CourseMapper.INSTANCE.courseDTOtoEntity(courseDTO);
        if (course.getTeacher().getTeacherId() == 0)
            course.setTeacher(null);
        return course;
    }
}

