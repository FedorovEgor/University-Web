package com.fedorov.spring.service;

import com.fedorov.spring.mappers.TimeTableMapper;
import com.fedorov.spring.repository.TimeTableRepository;
import com.fedorov.spring.service.dto.TimeTableDTO;
import com.fedorov.spring.service.exceptions.DatabaseUpdateException;
import com.fedorov.spring.service.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeTableService {

    private final TimeTableRepository timeTableRepository;

    @Autowired
    public TimeTableService(TimeTableRepository timeTableRepository) {
        this.timeTableRepository = timeTableRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTableService.class);

    public void insertTimeTable(TimeTableDTO timeTableDTO) {
        LOGGER.debug("Creating timetable '{}'", timeTableDTO);
        try {
            timeTableRepository.save(TimeTableMapper.INSTANCE.timeTableDTOtoEntity(timeTableDTO));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create timetable '{}'", timeTableDTO);
            throw new DatabaseUpdateException("Timetable was not created", e);
        }
        LOGGER.debug("Timetable was created");
    }

    public boolean updateTimeTable(TimeTableDTO timeTableDTO) {
        boolean isUpdated = false;
        LOGGER.debug("Updating timetable '{}'", timeTableDTO);
        try {
            timeTableDTO.setId(timeTableDTO.getId());
            timeTableDTO.setTableName(timeTableDTO.getTableName());
            timeTableDTO.setDate(timeTableDTO.getDate());

            timeTableRepository.save(TimeTableMapper.INSTANCE.timeTableDTOtoEntity(timeTableDTO));
            isUpdated = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to update timetable '{}'", timeTableDTO);
            throw new DatabaseUpdateException("Timetable was not updated", e);
        }
        LOGGER.debug("Timetable was updated - {}", isUpdated);
        return isUpdated;
    }

    public boolean deleteTimeTable(int id) {
        boolean isDeleted = false;
        LOGGER.debug("Deleting timetable by timeTableId '{}'", id);
        try {
            timeTableRepository.deleteById(id);
            isDeleted = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete timetable with timeTableId '{}'", id);
            throw new DatabaseUpdateException("Timetable was not deleted", e);
        }
        LOGGER.debug("Timetable was deleted - {}", isDeleted);
        return isDeleted;
    }

    public TimeTableDTO selectTimeTable(int id) {
        TimeTableDTO timeTable;
        LOGGER.debug("Retrieving timetable by timeTableId '{}'", id);
        try {
            timeTable = TimeTableMapper.INSTANCE.timeTableToDTO(timeTableRepository.getOne(id));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve timetable with timeTableId '{}'", id);
            throw new EntityNotFoundException("Timetable was not retrieved", e);
        }
        LOGGER.debug("Timetable was retrieved");
        return timeTable;
    }

    public List<TimeTableDTO> selectAllTimeTables() {
        List<TimeTableDTO> timeTables;
        LOGGER.debug("Retrieving all timetables");
        try {
            timeTables = timeTableRepository.findAll().stream().map(TimeTableMapper.INSTANCE::timeTableToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve timetables");
            throw new EntityNotFoundException("Timetables were not retrieved", e);
        }
        LOGGER.debug("Timetables were retrieved");
        return timeTables;
    }
}
