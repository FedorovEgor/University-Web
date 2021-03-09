package com.fedorov.spring.mappers;

import com.fedorov.spring.model.Schedule;
import com.fedorov.spring.service.dto.ScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Mappings({
            @Mapping(target = "groupDTO.groupId", source = "group.groupId"),
            @Mapping(target = "courseDTO.courseId", source = "course.courseId"),
            @Mapping(target = "courseDTO.teacherDTO", source = "course.teacher"),
            @Mapping(target = "teacherDTO.teacherId", source = "teacher.teacherId"),
            @Mapping(target = "timeTableDTO.id", source = "timeTable.id")
    })
    ScheduleDTO scheduleToDTO (Schedule schedule);

    @Mappings({
            @Mapping(target = "group.groupId", source = "groupDTO.groupId"),
            @Mapping(target = "course.courseId", source = "courseDTO.courseId"),
            @Mapping(target = "course.teacher", source = "courseDTO.teacherDTO"),
            @Mapping(target = "teacher.teacherId", source = "teacherDTO.teacherId"),
            @Mapping(target = "timeTable.id", source = "timeTableDTO.id")
    })
    Schedule scheduleDTOtoEntity(ScheduleDTO scheduleDTO);
}
