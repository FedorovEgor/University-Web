package com.fedorov.spring.mappers;

import com.fedorov.spring.model.StudentCourse;
import com.fedorov.spring.service.dto.StudentCourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentCourseMapper {

    StudentCourseMapper INSTANCE = Mappers.getMapper(StudentCourseMapper.class);

    StudentCourseDTO studentCourseToDTO(StudentCourse studentCourse);

    StudentCourse studentCourseDTOtoEntity(StudentCourseDTO studentCourseDTO);
}
