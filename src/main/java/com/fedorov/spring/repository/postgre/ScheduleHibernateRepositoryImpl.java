package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.dao.DAOException;
import com.fedorov.spring.model.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
@Profile("hibernate")
@Transactional
public class ScheduleHibernateRepositoryImpl extends AbstractScheduleHibernateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleHibernateRepositoryImpl.class);

    private final String SQL_DELETE_SCHEDULE = "delete from Schedule s where s.scheduleId = :id";
    private final String SQL_SELECT_BY_MONTH_GROUPID = "select s from Schedule s where s.group.groupId = :groupId and MONTH(s.date) = :monthNo";
    private final String SQL_SELECT_BY_MONTH_TEACHERID = "select s from Schedule s where s.teacher.teacherId = :teacherId and MONTH(s.date) = :monthNo";
    private final String SQL_SELECT_BY_DATE_GROUPID = "select s from Schedule s where s.group.groupId = :groupId and s.date = :date";
    private final String SQL_SELECT_BY_DATE_TEACHERID = "select s from Schedule s where s.teacher.teacherId = :teacherId and s.date = :date";
    private final String SQL_SELECT_ALL_SCHEDULES = "select s from Schedule s";
    private final String SQL_SELECT_ALL_SCHEDULES_BY_DATE = "select s from Schedule s where s.date = :date";

    @Override
    public <S extends Schedule> S save(S s) {
        LOGGER.debug("Creating schedule '{}'", s);
        try {
            entityManager.merge(s);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create schedule '{}'", s);
            throw new DAOException("Schedule was not created", e);
        }
        LOGGER.debug("Schedule was created");
        return s;
    }

    @Override
    public Schedule getOne(Integer integer) {
        Schedule schedule;
        LOGGER.debug("Retrieving schedule by scheduleId '{}'", integer);
        try {
            schedule = entityManager.find(Schedule.class, integer);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve schedule with scheduleId '{}'", integer);
            throw new DAOException("Schedule was not retrieved", e);
        }
        LOGGER.debug("Schedule was retrieved");
        return schedule;
    }

    @Override
    public List<Schedule> findAll() {
        List<Schedule> schedules;
        LOGGER.debug("Retrieving all schedules");
        try {
            schedules = entityManager.createQuery(SQL_SELECT_ALL_SCHEDULES, Schedule.class).getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieved schedules");
            throw new DAOException("Schedules were not retrieved", e);
        }
        LOGGER.debug("Schedules were retrieved");
        return schedules;
    }

    @Override
    public List<Schedule> findAllByDate(Date date) {
        List<Schedule> schedules;
        LOGGER.debug("Retrieving schedules by date {}", date);
        try {
            schedules = entityManager.createQuery(SQL_SELECT_ALL_SCHEDULES_BY_DATE, Schedule.class).setParameter("date", date).getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve schedules with date '{}'", date);
            throw new DAOException("Schedules were not retrieved", e);
        }
        LOGGER.debug("Schedules were retrieved");
        return schedules;
    }

    @Override
    public List<Schedule> findAllByGroupIdAndDate(int groupId, Date date) {
        List<Schedule> schedules;
        LOGGER.debug("Retrieving schedules by groupId '{}' and date {}", groupId, date);
        try {
            schedules = entityManager.createQuery(SQL_SELECT_BY_DATE_GROUPID, Schedule.class)
                    .setParameter("groupId", groupId)
                    .setParameter("date", date)
                    .getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve schedules with groupId '{}' and date '{}'", groupId, date);
            throw new DAOException("Schedules were not retrieved", e);
        }
        LOGGER.debug("Schedules were retrieved");
        return schedules;
    }

    @Override
    public List<Schedule> findAllByTeacherIdAndDate(int teacherId, Date date) {
        List<Schedule> schedules;
        LOGGER.debug("Retrieving schedules by teacherId '{}' and date '{}'", teacherId, date);
        try {
            schedules = entityManager.createQuery(SQL_SELECT_BY_DATE_TEACHERID, Schedule.class)
                    .setParameter("teacherId", teacherId)
                    .setParameter("date", date)
                    .getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve schedules with teacherId '{}' and date '{}'", teacherId, date);
            throw new DAOException("Schedules were not retrieved", e);
        }
        LOGGER.debug("Schedules were retrieved");
        return schedules;
    }

    @Override
    public List<Schedule> findAllByGroupIdAndMonth(int groupId, int monthNo) {
        List<Schedule> schedules;
        LOGGER.debug("Retrieving schedules by groupId '{}' and month '{}'", groupId, monthNo);
        try {
            schedules = entityManager.createQuery(SQL_SELECT_BY_MONTH_GROUPID, Schedule.class)
                    .setParameter("groupId", groupId)
                    .setParameter("monthNo", monthNo)
                    .getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve schedules with groupId '{}' and month '{}'", groupId, monthNo);
            throw new DAOException("Schedules were not retrieved", e);
        }
        LOGGER.debug("Schedules were retrieved");
        return schedules;
    }

    @Override
    public List<Schedule> findAllByTeacherIdAndMonth(int teacherId, int monthNo) {
        List<Schedule> schedules;
        LOGGER.debug("Retrieving schedules with teacherId '{}' and month '{}'", teacherId, monthNo);
        try {
            schedules = entityManager.createQuery(SQL_SELECT_BY_MONTH_TEACHERID, Schedule.class)
                    .setParameter("teacherId", teacherId)
                    .setParameter("monthNo", monthNo)
                    .getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve schedules with teacherId '{}' and month '{}'", teacherId, monthNo);
            throw new DAOException("Schedules were not retrieved", e);
        }
        LOGGER.debug("Schedules were retrieved");
        return schedules;
    }

    @Override
    public void deleteById(Integer integer) {
        LOGGER.debug("Deleting schedule by scheduleId '{}'", integer);
        try {
            entityManager.createQuery(SQL_DELETE_SCHEDULE).setParameter("id", integer).executeUpdate();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete schedule with scheduleId '{}'", integer);
            throw new DAOException("Schedule was not deleted", e);
        }
        LOGGER.debug("Schedule was deleted");
    }
}

