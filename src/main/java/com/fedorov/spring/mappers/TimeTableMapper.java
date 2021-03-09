package com.fedorov.spring.mappers;

import com.fedorov.spring.model.TimeTable;
import com.fedorov.spring.service.dto.TimeTableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TimeTableMapper {

    TimeTableMapper INSTANCE = Mappers.getMapper(TimeTableMapper.class);

    TimeTableDTO timeTableToDTO(TimeTable timeTable);

    TimeTable timeTableDTOtoEntity(TimeTableDTO timeTableDTO);
}
