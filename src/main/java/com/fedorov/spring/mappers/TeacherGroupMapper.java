package com.fedorov.spring.mappers;

import com.fedorov.spring.model.TeacherGroup;
import com.fedorov.spring.service.dto.TeacherGroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeacherGroupMapper {

    TeacherGroupMapper INSTANCE = Mappers.getMapper(TeacherGroupMapper.class);

    TeacherGroupDTO teacherGroupToDTO(TeacherGroup teacherGroup);

    TeacherGroup teacherGroupDTOtoEntity(TeacherGroupDTO teacherGroupDTO);
}
