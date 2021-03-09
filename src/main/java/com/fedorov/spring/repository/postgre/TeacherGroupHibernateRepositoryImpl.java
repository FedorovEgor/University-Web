package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.dao.DAOException;
import com.fedorov.spring.model.TeacherGroup;
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
public class TeacherGroupHibernateRepositoryImpl extends AbstractTeacherGroupHibernateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherGroupHibernateRepositoryImpl.class);

    private final String SQL_SELECT_TEACHER_GROUP_BY_BOTH_IDS = "select tg from TeacherGroup tg where tg.teacher_id = :teacherId and tg.group_id = :groupId";
    private final String SQL_SELECT_ALL_TEACHERS_GROUPS = "select tg from TeacherGroup tg";
    private final String SQL_DELETE_TEACHER_GROUP = "delete from TeacherGroup tg where tg.id = :id";

    @Override
    public <S extends TeacherGroup> S save(S s) {
        LOGGER.debug("Creating new teacher_group '{}'", s);
        try {
            entityManager.merge(s);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create teacher_group '{}'", s);
            throw new DAOException("Teacher_group was not created", e);
        }
        LOGGER.debug("Teacher_group was created");
        return s;
    }

    @Override
    public TeacherGroup getOne(Integer integer) {
        TeacherGroup teacherGroup;
        LOGGER.debug("Retrieving teacher_group by teacherGroupId '{}'", integer);
        try {
            teacherGroup = entityManager.find(TeacherGroup.class, integer);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve teacher_group with teacherGroupId '{}'", integer);
            throw new DAOException("Teacher group was not retrieved", e);
        }
        LOGGER.debug("Teacher_group was retrieved");
        return teacherGroup;
    }

    @Override
    public TeacherGroup findByTeacherIdAndGroupId(int teacher_id, int group_id) {
        TeacherGroup teacherGroup;
        LOGGER.debug("Retrieving teacher_group by teacherId '{}' and groupId '{}'", teacher_id, group_id);
        try {
            teacherGroup = entityManager.createQuery(SQL_SELECT_TEACHER_GROUP_BY_BOTH_IDS, TeacherGroup.class)
                    .setParameter("teacherId", teacher_id)
                    .setParameter("groupId", group_id).getSingleResult();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve teacher_group with teacherId '{}' and groupId '{}'", teacher_id, group_id);
            throw new DAOException("Teacher_group was not retrieved", e);
        }
        LOGGER.debug("Teacher_group was retrieved");
        return teacherGroup;
    }

    @Override
    public List<TeacherGroup> findAll() {
        List<TeacherGroup> teacherGroups;
        LOGGER.debug("Retrieving all teachers_groups");
        try {
            teacherGroups = entityManager.createQuery(SQL_SELECT_ALL_TEACHERS_GROUPS, TeacherGroup.class).getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve teachers_groups");
            throw new DAOException("Teachers_groups were not retrieved", e);
        }
        LOGGER.debug("Teachers_groups were retrieved");
        return teacherGroups;
    }

    @Override
    public void deleteById(Integer integer) {
        LOGGER.debug("Deleting teacher_group by teacherGroupId '{}'", integer);
        try {
            entityManager.createQuery(SQL_DELETE_TEACHER_GROUP).setParameter("id", integer).executeUpdate();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete teacher_group with teacherGroupId '{}'", integer);
            throw new DAOException("Teacher_group was not deleted", e);
        }
        LOGGER.debug("Teacher_group was deleted");
    }
}

