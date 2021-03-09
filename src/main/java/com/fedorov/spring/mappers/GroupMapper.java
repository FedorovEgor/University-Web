package com.fedorov.spring.mappers;

import com.fedorov.spring.model.Group;
import com.fedorov.spring.service.dto.GroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    GroupDTO groupToDTO(Group group);

    Group groupDTOtoEntity(GroupDTO groupDTO);
}
