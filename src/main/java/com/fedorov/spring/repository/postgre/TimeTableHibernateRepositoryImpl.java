package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.dao.DAOException;
import com.fedorov.spring.model.TimeTable;
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
public class TimeTableHibernateRepositoryImpl extends AbstractTimeTableHibernateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTableHibernateRepositoryImpl.class);

    private final String SQL_DELETE_TIMETABLE = "delete from TimeTable tt where tt.id = :id";
    private final String SQL_SELECT_ALL_TIMETABLES = "select tt from TimeTable tt";

    @Override
    public <S extends TimeTable> S save(S s) {
        LOGGER.debug("Creating new timetable '{}'", s);
        try {
            entityManager.merge(s);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create new timetable '{}'", s);
            throw new DAOException("Timetable was not created", e);
        }
        LOGGER.debug("Timetable was created");
        return s;
    }

    @Override
    public TimeTable getOne(Integer integer) {
        TimeTable timeTable;
        LOGGER.debug("Retrieving timetable by timeTableId '{}'", integer);
        try {
            timeTable = entityManager.find(TimeTable.class, integer);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve timetable with timeTableId '{}'", integer);
            throw new DAOException("Timetable was not retrieved", e);
        }
        LOGGER.debug("Timetable was retrieved");
        return timeTable;
    }

    @Override
    public List<TimeTable> findAll() {
        List<TimeTable> timeTables;
        LOGGER.debug("Retrieving all timetables");
        try {
            timeTables = entityManager.createQuery(SQL_SELECT_ALL_TIMETABLES, TimeTable.class).getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve timatables");
            throw new DAOException("Unable to retrieve timetables", e);
        }
        LOGGER.debug("Timetables were retrieved");
        return timeTables;
    }

    @Override
    public void deleteById(Integer integer) {
        LOGGER.debug("Deleting timetable by timeTableId '{}'", integer);
        try {
            entityManager.createQuery(SQL_DELETE_TIMETABLE).setParameter("id", integer).executeUpdate();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete timetable with timeTableId '{}'", integer);
            throw new DAOException("Timetable was not deleted", e);
        }
        LOGGER.debug("Timetable was deleted");
    }
}

