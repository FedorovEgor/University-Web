package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.model.Group;
import com.fedorov.spring.repository.GroupRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("hibernate")
public abstract class AbstractGroupHibernateRepository implements GroupRepository {
    @Override
    public List<Group> findAll() {
        return null;
    }

    @Override
    public List<Group> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Group> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public <S extends Group> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Group> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Group> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Group getOne(Integer integer) {
        return null;
    }

    @Override
    public <S extends Group> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Group> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Group> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Group> S save(S s) {
        return null;
    }

    @Override
    public Optional<Group> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Group group) {

    }

    @Override
    public void deleteAll(Iterable<? extends Group> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Group> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Group> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Group> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Group> boolean exists(Example<S> example) {
        return false;
    }
}
