package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.dao.DAOException;
import com.fedorov.spring.model.Teacher;
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
public class TeacherHibernateRepositoryImpl extends AbstractTeacherHibernateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherHibernateRepositoryImpl.class);

    private final String SQL_DELETE_TEACHER = "delete from Teacher t where t.teacherId = :id";
    private final String SQL_UPDATE_TEACHER = "update Teacher t set t.firstName = :firstName, t.lastName = :lastName where t.teacherId = :id";
    private final String SQL_SELECT_ALL_TEACHERS = "select t from Teacher t";

    @Override
    public <S extends Teacher> S save(S s) {
        LOGGER.debug("Creating a new teacher '{}'", s);
        try {
            entityManager.merge(s);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create teacher '{}'", s);
            throw new DAOException("Teacher was not created", e);
        }
        LOGGER.debug("New teacher was created");
        return s;
    }

    @Override
    public Teacher getOne(Integer integer) {
        Teacher teacher;
        LOGGER.debug("Retrieving teacher by teacherId '{}'", integer);
        try {
            teacher = entityManager.find(Teacher.class, integer);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve teacher with teacherId '{}'", integer);
            throw new DAOException("Teacher was not retrieved", e);
        }
        LOGGER.debug("Teacher was retrieved");
        return teacher;
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> teachers;
        LOGGER.debug("Retrieving all teachers");
        try {
            teachers = entityManager.createQuery(SQL_SELECT_ALL_TEACHERS, Teacher.class).getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve teachers");
            throw new DAOException("Unable to retrieve teachers", e);
        }
        LOGGER.debug("Teachers were retrieved");
        return teachers;
    }

    @Override
    public void deleteById(Integer integer) {
        LOGGER.debug("Deleting teacher by teacherId '{}'", integer);
        try {
            entityManager.createQuery(SQL_DELETE_TEACHER)
                    .setParameter("id", integer).executeUpdate();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete teacher with teacherId '{}'", integer);
            throw new DAOException("Teacher was not deleted", e);
        }
        LOGGER.debug("Teacher was deleted");
    }
}

