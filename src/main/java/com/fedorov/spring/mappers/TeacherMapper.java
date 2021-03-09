package com.fedorov.spring.mappers;

import com.fedorov.spring.model.Teacher;
import com.fedorov.spring.service.dto.TeacherDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    TeacherDTO teacherToDTO(Teacher teacher);

    Teacher teacherDTOtoEntity(TeacherDTO teacherDTO);
}
