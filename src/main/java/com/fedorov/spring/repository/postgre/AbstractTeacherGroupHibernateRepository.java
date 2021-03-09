package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.model.TeacherGroup;
import com.fedorov.spring.repository.TeacherGroupRepository;
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
public abstract class AbstractTeacherGroupHibernateRepository implements TeacherGroupRepository {
    @Override
    public TeacherGroup findByTeacherIdAndGroupId(int teacher_id, int group_id) {
        return null;
    }

    @Override
    public List<TeacherGroup> findAll() {
        return null;
    }

    @Override
    public List<TeacherGroup> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<TeacherGroup> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public <S extends TeacherGroup> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends TeacherGroup> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<TeacherGroup> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public TeacherGroup getOne(Integer integer) {
        return null;
    }

    @Override
    public <S extends TeacherGroup> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends TeacherGroup> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<TeacherGroup> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TeacherGroup> S save(S s) {
        return null;
    }

    @Override
    public Optional<TeacherGroup> findById(Integer integer) {
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
    public void delete(TeacherGroup teacherGroup) {

    }

    @Override
    public void deleteAll(Iterable<? extends TeacherGroup> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends TeacherGroup> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TeacherGroup> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TeacherGroup> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TeacherGroup> boolean exists(Example<S> example) {
        return false;
    }
}
