package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.model.TimeTable;
import com.fedorov.spring.repository.TimeTableRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.PostRemove;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("hibernate")
public abstract class AbstractTimeTableHibernateRepository implements TimeTableRepository {
    @Override
    public List<TimeTable> findAll() {
        return null;
    }

    @Override
    public List<TimeTable> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<TimeTable> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public <S extends TimeTable> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends TimeTable> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<TimeTable> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public TimeTable getOne(Integer integer) {
        return null;
    }

    @Override
    public <S extends TimeTable> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends TimeTable> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<TimeTable> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TimeTable> S save(S s) {
        return null;
    }

    @Override
    public Optional<TimeTable> findById(Integer integer) {
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
    public void delete(TimeTable timeTable) {

    }

    @Override
    public void deleteAll(Iterable<? extends TimeTable> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends TimeTable> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TimeTable> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TimeTable> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TimeTable> boolean exists(Example<S> example) {
        return false;
    }
}
