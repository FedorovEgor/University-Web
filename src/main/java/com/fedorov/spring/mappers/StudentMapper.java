package com.fedorov.spring.mappers;

import com.fedorov.spring.model.Student;
import com.fedorov.spring.service.dto.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(target = "groupDTO.groupId", source = "group.groupId")
    StudentDTO studentToDTO(Student student);

    @Mapping(target = "group.groupId", source = "groupDTO.groupId")
    Student studentDTOtoEntity(StudentDTO studentDTO);
}
