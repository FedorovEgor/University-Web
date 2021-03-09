package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.dao.DAOException;
import com.fedorov.spring.model.StudentCourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Profile("hibernate")
@Transactional
public class StudentCourseHibernateRepositoryImpl extends AbstractStudentCourseHibernateRepository{

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCourseHibernateRepositoryImpl.class);

    private final String SQL_SELECT_STUDENT_COURSE_BY_BOTH_IDS = "select sc from StudentCourse sc where sc.student_id = :studentId and sc.course_id = :courseId";
    private final String SQL_SELECT_ALL_STUDENTS_COURSES = "select sc from StudentCourse sc";
    private final String SQL_DELETE_STUDENTS_COURSES = "delete from StudentCourse sc where sc.id = :id";
    private final String SQL_UPDATE_STUDENTS_COURSES = "update StudentCourse sc set sc.student_id = :studentId,sc.course_id = :courseId where sc.id = :id";

    @Override
    public <S extends StudentCourse> S save(S s) {
        LOGGER.debug("Creating student_course '{}'", s);
        try {
            entityManager.merge(s);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create student_course '{}'", s);
            throw new DAOException("Student_course was not created", e);
        }
        LOGGER.debug("Student_course was created");
        return s;
    }

    @Override
    public StudentCourse getOne(Integer integer) {
        StudentCourse studentCourse;
        LOGGER.debug("Retrieving student_course by studentCourseId '{}'", integer);
        try {
            studentCourse = entityManager.find(StudentCourse.class, integer);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve student_course with studentCourseId '{}'", integer);
            throw new DAOException("Student_course was not retrieved", e);
        }
        LOGGER.debug("Student_course was retrieved");
        return studentCourse;
    }

    @Override
    public StudentCourse findByStudentIdAndCourseId(int studentId, int courseId) {
        StudentCourse studentCourse;
        LOGGER.debug("Retrieving student_course by studentId '{}' and courseId '{}'", studentId, courseId);
        try {
            studentCourse = entityManager.createQuery(SQL_SELECT_STUDENT_COURSE_BY_BOTH_IDS, StudentCourse.class).setParameter("studentId", studentId)
                    .setParameter("courseId", courseId).getSingleResult();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve student_course with studentId '{}' and courseId '{}'", studentId, courseId);
            throw new DAOException("Student_course was not retrieved", e);
        }
        LOGGER.debug("Student_course was retrieved");
        return studentCourse;
    }

    @Override
    public List<StudentCourse> findAll() {
        List<StudentCourse> studentCourses;
        LOGGER.debug("Retrieving all students_courses");
        try {
            studentCourses = entityManager.createQuery(SQL_SELECT_ALL_STUDENTS_COURSES, StudentCourse.class).getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve students_courses");
            throw new DAOException("Students_courses were not retrieved", e);
        }
        LOGGER.debug("Students_courses were retrieved");
        return studentCourses;
    }

    @Override
    public void deleteById(Integer integer) {
        LOGGER.debug("Deleting student_course by studentCourseId '{}'", integer);
        try {
            entityManager.createQuery(SQL_DELETE_STUDENTS_COURSES).setParameter("id", integer).executeUpdate();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete student_course with studentCourseId '{}'", integer);
            throw new DAOException("Student_course was not deleted", e);
        }
        LOGGER.debug("Student_course was deleted");
    }
}

