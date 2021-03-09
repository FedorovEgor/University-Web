package com.fedorov.spring.mappers;

import com.fedorov.spring.model.Course;
import com.fedorov.spring.service.dto.CourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(target = "teacherDTO.teacherId", source = "teacher.teacherId")
    @Mapping(target = "schedulesDTO", source = "schedules")
    CourseDTO courseToDTO(Course course);

    @Mapping(target = "teacher.teacherId", source = "teacherDTO.teacherId")
    @Mapping(target = "schedules", source = "schedulesDTO")
    Course courseDTOtoEntity(CourseDTO courseDTO);
}
