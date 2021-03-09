package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.dao.DAOException;
import com.fedorov.spring.model.Course;
import com.fedorov.spring.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("hibernate")
@Transactional
public class CourseHibernateRepositoryImpl extends AbstractCourseHibernateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseHibernateRepositoryImpl.class);

    private final String SQL_DELETE_COURSE = "delete from Course c where c.courseId = :id";
    private final String SQL_SELECT_ALL_COURSES = "select c from Course c";

    @Override
    public <S extends Course> S save(S s) {
        LOGGER.debug("Creating course '{}'", s);
        try {
            entityManager.merge(s);
        } catch (DataAccessException e) {
            LOGGER.error("Course '{}' was not created", s);
            throw new DAOException("Course was not created", e);
        }
        LOGGER.debug("A new course was created");
        return s;
    }

    @Override
    public Course getOne(Integer integer) {
        Course course;
        LOGGER.debug("Retrieving course with courseId '{}'", integer);
        try {
            course = entityManager.find(Course.class, integer);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to select course by courseId '{}'", integer);
            throw new DAOException("Unable to select course", e);
        }
        LOGGER.debug("Course was retrieved");
        return course;
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses;
        LOGGER.debug("Retrieving all courses");
        try {
            courses = entityManager.createQuery(SQL_SELECT_ALL_COURSES, Course.class).getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to get courses");
            throw new DAOException("Unable to get courses", e);
        }
        LOGGER.debug("Courses were retrieved");
        return courses;
    }

    @Override
    public void deleteById(Integer integer) {
        LOGGER.debug("Deleting course by courseId '{}'", integer);
        try {
            entityManager.createQuery(SQL_DELETE_COURSE).setParameter("id", integer).executeUpdate();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete course with courseId '{}'", integer);
            throw new DAOException("Unable to delete course", e);
        }
        LOGGER.debug("Course was deleted");
    }
}

