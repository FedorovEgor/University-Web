package com.fedorov.spring.service;

import com.fedorov.spring.mappers.ScheduleMapper;
import com.fedorov.spring.model.Schedule;
import com.fedorov.spring.repository.ScheduleRepository;
import com.fedorov.spring.service.dto.ScheduleDTO;
import com.fedorov.spring.service.exceptions.DatabaseUpdateException;
import com.fedorov.spring.service.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);

    public void insertSchedule(ScheduleDTO scheduleDTO) {
        LOGGER.debug("Creating schedule '{}'", scheduleDTO);
        try {
            scheduleRepository.save(scheduleInsertHelper(scheduleDTO));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to create schedule '{}'", scheduleDTO);
            throw new DatabaseUpdateException("Schedule was not created", e);
        }
        LOGGER.debug("Schedule was created");
    }

    public boolean updateSchedule(ScheduleDTO scheduleDTO) {
        boolean isUpdated = false;
        LOGGER.debug("Updating schedule '{}'", scheduleDTO);
        try {
            scheduleDTO.setScheduleId(scheduleDTO.getScheduleId());
            scheduleDTO.setGroupDTO(scheduleDTO.getGroupDTO());
            scheduleDTO.setCourseDTO(scheduleDTO.getCourseDTO());
            scheduleDTO.setTeacherDTO(scheduleDTO.getTeacherDTO());
            scheduleDTO.setTimeTableDTO(scheduleDTO.getTimeTableDTO());
            scheduleDTO.setDate(scheduleDTO.getDate());

            scheduleRepository.save(scheduleInsertHelper(scheduleDTO));
            isUpdated = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to update schedule '{}'", scheduleDTO);
            throw new DatabaseUpdateException("Schedule was not updated", e);
        }
        LOGGER.debug("Schedule was updated - {}", isUpdated);
        return isUpdated;
    }

    public boolean deleteSchedule(int id) {
        boolean isDeleted = false;
        LOGGER.debug("Deleting schedule by scheduleId '{}'", id);
        try {
            scheduleRepository.deleteById(id);
            isDeleted = true;
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete schedule with scheduleId '{}'", id);
            throw new DatabaseUpdateException("Schedule was not deleted", e);
        }
        LOGGER.debug("Schedule was deleted - {}", isDeleted);
        return isDeleted;
    }

    public ScheduleDTO selectSchedule(int id) {
        ScheduleDTO scheduleDTO;
        LOGGER.debug("Retrieving schedule by scheduleId '{}'", id);
        try {
            scheduleDTO = ScheduleMapper.INSTANCE.scheduleToDTO(scheduleRepository.getOne(id));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve schedule with scheduleId '{}'", id);
            throw new EntityNotFoundException("Schedule was not retrieved", e);
        }
        LOGGER.debug("Schedule was retrieved");
        return scheduleDTO;
    }

    public List<ScheduleDTO> selectAllSchedules() {
        List<ScheduleDTO> schedules;
        LOGGER.debug("Retrieving all schedules");
        try {
            schedules = scheduleRepository.findAll().stream().map(ScheduleMapper.INSTANCE::scheduleToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieved schedules");
            throw new EntityNotFoundException("Schedules were not retrieved", e);
        }
        LOGGER.debug("Schedules were retrieved");
        return schedules;
    }

    public List<ScheduleDTO> selectAllSchedulesByDate(Date date) {
        List<ScheduleDTO> schedules;
        LOGGER.debug("Retrieving all schedules");
        try {
            schedules = scheduleRepository.findAllByDate(date).stream().map(ScheduleMapper.INSTANCE::scheduleToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieved schedules");
            throw new EntityNotFoundException("Schedules were not retrieved", e);
        }
        LOGGER.debug("Schedules were retrieved");
        return schedules;
    }

    public List<ScheduleDTO> selectScheduleByGroupIdAndDate(int groupId, Date date) {
        List<ScheduleDTO> schedules;
        LOGGER.debug("Retrieving schedules by groupId '{}' and date {}", groupId, date);
        try {
            schedules = scheduleRepository.findAllByGroupIdAndDate(groupId, date).stream()
                    .map(ScheduleMapper.INSTANCE::scheduleToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve schedules with groupId '{}' and date '{}'", groupId, date);
            throw new EntityNotFoundException("Schedules were not retrieved", e);
        }
        LOGGER.debug("Schedules were retrieved");
        return schedules;
    }

    public List<ScheduleDTO> selectSchedulesByMonthAndGroupId(int groupId, int monthNo) {
        List<ScheduleDTO> schedules;
        LOGGER.debug("Retrieving schedules by groupId '{}' and month '{}'", groupId, monthNo);
        try {
            schedules = scheduleRepository.findAllByGroupIdAndMonth(groupId, monthNo).stream()
                    .map(ScheduleMapper.INSTANCE::scheduleToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve schedules with groupId '{}' and month '{}'", groupId, monthNo);
            throw new EntityNotFoundException("Schedules were not retrieved", e);
        }
        LOGGER.debug("Schedules were retrieved");
        return schedules;
    }

    public List<ScheduleDTO> selectScheduleByTeacherIdAndDate(int teacherId, Date date) {
        List<ScheduleDTO> schedules;
        LOGGER.debug("Retrieving schedules by teacherId '{}' and date '{}'", teacherId, date);
        try {
            schedules = scheduleRepository.findAllByTeacherIdAndDate(teacherId, date).stream()
                    .map(ScheduleMapper.INSTANCE::scheduleToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve schedules with teacherId '{}' and date '{}'", teacherId, date);
            throw new EntityNotFoundException("Schedules were not retrieved", e);
        }
        LOGGER.debug("Schedules were retrieved");
        return schedules;
    }

    public List<ScheduleDTO> selectSchedulesByMonthAndTeacherId(int teacherId, int monthNo) {
        List<ScheduleDTO> schedules;
        LOGGER.debug("Retrieving schedules with teacherId '{}' and month '{}'", teacherId, monthNo);
        try {
            schedules = scheduleRepository.findAllByTeacherIdAndMonth(teacherId, monthNo).stream()
                    .map(ScheduleMapper.INSTANCE::scheduleToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve schedules with teacherId '{}' and month '{}'", teacherId, monthNo);
            throw new EntityNotFoundException("Schedules were not retrieved", e);
        }
        LOGGER.debug("Schedules were retrieved");
        return schedules;
    }

    private Schedule scheduleInsertHelper(ScheduleDTO scheduleDTO) {
        Schedule schedule = ScheduleMapper.INSTANCE.scheduleDTOtoEntity(scheduleDTO);
        if (schedule.getGroup().getGroupId() == 0)
            schedule.setGroup(null);
        if (schedule.getCourse().getCourseId() == 0)
            schedule.setCourse(null);
        if (schedule.getTeacher().getTeacherId() == 0)
            schedule.setTeacher(null);
        if (schedule.getTimeTable().getId() == 0)
            schedule.setTimeTable(null);
        return schedule;
    }
}

