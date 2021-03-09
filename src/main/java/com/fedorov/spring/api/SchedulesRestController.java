package com.fedorov.spring.api;

import com.fedorov.spring.service.ScheduleService;
import com.fedorov.spring.service.dto.ScheduleDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class SchedulesRestController {

    private ScheduleService scheduleService;

    public SchedulesRestController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/all")
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.selectAllSchedules();
    }

    @GetMapping("/single/{id}")
    public ScheduleDTO getSingleSchedule(@PathVariable int id) {
        return scheduleService.selectSchedule(id);
    }

    @GetMapping("/all/group-date/{groupId}/{date}")
    public List<ScheduleDTO> getSchedulesByGroupIdAndDate(@PathVariable int groupId, @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        return scheduleService.selectScheduleByGroupIdAndDate(groupId, date);
    }

    @GetMapping("/all/group-month/{groupId}/{monthNo}")
    public List<ScheduleDTO> getScheduleByGroupIdAndMonthNo(@PathVariable int groupId, @PathVariable int monthNo) {
        return scheduleService.selectSchedulesByMonthAndGroupId(groupId, monthNo);
    }

    @GetMapping("/all/teacher-date/{teacherId}/{date}")
    public List<ScheduleDTO> getScheduleByTeacherIdAnDate(@PathVariable int teacherId, @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        return scheduleService.selectScheduleByTeacherIdAndDate(teacherId, date);
    }

    @GetMapping("/all/teacher-month/{teacherId}/{monthNo}")
    public List<ScheduleDTO> getScheduleByTeacherIdAndMonthNo(@PathVariable int teacherId, @PathVariable int monthNo) {
        return scheduleService.selectSchedulesByMonthAndTeacherId(teacherId, monthNo);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        scheduleService.insertSchedule(scheduleDTO);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateSchedule(@PathVariable int id, @RequestBody ScheduleDTO scheduleDTO) {
        if (scheduleService.selectSchedule(id) != null)
            scheduleService.updateSchedule(scheduleDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSchedule(@PathVariable int id) {
        if (scheduleService.selectSchedule(id) != null)
            scheduleService.deleteSchedule(id);
    }
}

