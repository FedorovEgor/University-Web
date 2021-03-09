package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.dao.DAOException;
import com.fedorov.spring.model.Group;
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
public class GroupHibernateRepositoryImpl extends AbstractGroupHibernateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupHibernateRepositoryImpl.class);

    private final String SQL_DELETE_GROUP = "delete from Group g where g.groupId = :id";
    private final String SQL_SELECT_ALL_GROUPS = "select g from Group g";

    @Override
    public <S extends Group> S save(S s) {
        LOGGER.debug("Creating group '{}'", s);
        try {
            entityManager.merge(s);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create group");
            throw new DAOException("Group was not created", e);
        }
        LOGGER.debug("A new group was created");
        return s;
    }

    @Override
    public Group getOne(Integer integer) {
        Group group;
        LOGGER.debug("Retrieving group with groupId '{}'", integer);
        try {
            group = entityManager.find(Group.class, integer);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve group with groupId '{}'", integer);
            throw new DAOException("Group was not retrieved", e);
        }
        LOGGER.debug("Group was retrieved");
        return group;
    }

    @Override
    public List<Group> findAll() {
        List<Group> groups;
        LOGGER.debug("Retrieving all groups");
        try {
            groups = entityManager.createQuery(SQL_SELECT_ALL_GROUPS, Group.class).getResultList();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve groups");
            throw new DAOException("Unable to retrieve groups", e);
        }
        LOGGER.debug("Groups were retrieved");
        return groups;
    }

    @Override
    public void deleteById(Integer integer) {
        LOGGER.debug("Deleting group by groupId '{}'", integer);
        try {
            entityManager.createQuery(SQL_DELETE_GROUP).setParameter("id", integer).executeUpdate();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete group by groupId '{}'", integer);
            throw new DAOException("Group was not deleted", e);
        }
        LOGGER.debug("Group was deleted");
    }
}

