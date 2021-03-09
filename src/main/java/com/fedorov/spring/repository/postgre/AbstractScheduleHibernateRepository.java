package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.model.Schedule;
import com.fedorov.spring.repository.ScheduleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("hibernate")
public abstract class AbstractScheduleHibernateRepository implements ScheduleRepository {
    @Override
    public List<Schedule> findAllByDate(Date date) {
        return null;
    }

    @Override
    public List<Schedule> findAllByGroupIdAndDate(int groupId, Date date) {
        return null;
    }

    @Override
    public List<Schedule> findAllByGroupIdAndMonth(int groupId, int monthNo) {
        return null;
    }

    @Override
    public List<Schedule> findAllByTeacherIdAndDate(int teacherId, Date date) {
        return null;
    }

    @Override
    public List<Schedule> findAllByTeacherIdAndMonth(int teacherId, int monthNo) {
        return null;
    }

    @Override
    public List<Schedule> findAll() {
        return null;
    }

    @Override
    public List<Schedule> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Schedule> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public <S extends Schedule> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Schedule> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Schedule> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Schedule getOne(Integer integer) {
        return null;
    }

    @Override
    public <S extends Schedule> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Schedule> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Schedule> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Schedule> S save(S s) {
        return null;
    }

    @Override
    public Optional<Schedule> findById(Integer integer) {
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
    public void delete(Schedule schedule) {

    }

    @Override
    public void deleteAll(Iterable<? extends Schedule> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Schedule> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Schedule> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Schedule> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Schedule> boolean exists(Example<S> example) {
        return false;
    }
}
