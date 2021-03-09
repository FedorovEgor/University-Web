package com.fedorov.spring.repository.postgre;

import com.fedorov.spring.model.StudentCourse;
import com.fedorov.spring.repository.StudentCourseRepository;
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
public abstract class AbstractStudentCourseHibernateRepository implements StudentCourseRepository {
    @Override
    public StudentCourse findByStudentIdAndCourseId(int studentId, int courseId) {
        return null;
    }

    @Override
    public List<StudentCourse> findAll() {
        return null;
    }

    @Override
    public List<StudentCourse> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<StudentCourse> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public <S extends StudentCourse> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends StudentCourse> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<StudentCourse> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public StudentCourse getOne(Integer integer) {
        return null;
    }

    @Override
    public <S extends StudentCourse> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends StudentCourse> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<StudentCourse> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends StudentCourse> S save(S s) {
        return null;
    }

    @Override
    public Optional<StudentCourse> findById(Integer integer) {
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
    public void delete(StudentCourse studentCourse) {

    }

    @Override
    public void deleteAll(Iterable<? extends StudentCourse> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends StudentCourse> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends StudentCourse> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends StudentCourse> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends StudentCourse> boolean exists(Example<S> example) {
        return false;
    }
}
