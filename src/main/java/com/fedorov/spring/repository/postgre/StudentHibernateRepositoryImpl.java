package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.dao.DAOException;
import com.fedorov.spring.model.Student;
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
public class StudentHibernateRepositoryImpl extends AbstractStudentHibernateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentHibernateRepositoryImpl.class);

    private final String SQL_DELETE_STUDENT = "delete from Student s where s.studentId = :id";
    private final String SQL_UPDATE_STUDENT = "update Student s set s.group.groupId = :groupId,s.firstName = :firstName, s.lastName = :lastName where s.studentId = :id";
    private final String SQL_SELECT_ALL_STUDENTS = "select s from Student s";

    @Override
    public <S extends Student> S save(S s) {
        LOGGER.debug("Creating a new student '{}'", s);
        try {
            entityManager.merge(s);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create student '{}'", s);
            throw new DAOException("Student was not created", e);
        }
        LOGGER.debug("Student was created");
        return s;
    }

    @Override
    public Student getOne(Integer integer) {
        Student student;
        LOGGER.debug("Retrieving student by studentId '{}'", integer);
        try {
            student = entityManager.find(Student.class, integer);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve student with studentId '{}'", integer);
            throw new DAOException("Student was not retrieved", e);
        }
        LOGGER.debug("Student was retrieved");
        return student;
    }

    @Override
    public List<Student> findAll() {
        List<Student> students;
        LOGGER.debug("Retrieving all students");
        try {
            students = entityManager.createQuery(SQL_SELECT_ALL_STUDENTS, Student.class).getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve students");
            throw new DAOException("Unable to retrieve students", e);
        }
        LOGGER.debug("Students were retrieved");
        return students;
    }

    @Override
    public void deleteById(Integer integer) {
        LOGGER.debug("Deleting student by studentId '{}'", integer);
        try {
            entityManager.createQuery(SQL_DELETE_STUDENT).setParameter("id", integer).executeUpdate();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete student by studentId '{}'", integer);
            throw new DAOException("Unable to delete student", e);
        }
        LOGGER.debug("Student was deleted");
    }
}

