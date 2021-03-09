package com.fedorov.spring.controllers;

import com.fedorov.spring.service.ScheduleService;
import com.fedorov.spring.service.TimeTableService;
import com.fedorov.spring.service.dto.ScheduleDTO;
import com.fedorov.spring.service.dto.TimeTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/timeTables")
public class TimeTablesController {

    private final TimeTableService timeTableService;
    private final ScheduleService scheduleService;

    @Autowired
    public TimeTablesController(TimeTableService timeTableService, ScheduleService scheduleService) {
        this.timeTableService = timeTableService;
        this.scheduleService = scheduleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("timetables", timeTableService.selectAllTimeTables());
        return "timetables/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        TimeTableDTO timeTableDTO = timeTableService.selectTimeTable(id);
        List<ScheduleDTO> schedules = scheduleService.selectAllSchedulesByDate(timeTableDTO.getDate());
        model.addAttribute("timetable", timeTableDTO);
        model.addAttribute("schedules", schedules);

        return "timetables/show";
    }

    @GetMapping("/new")
    public String newTimetable(@ModelAttribute("timetable") TimeTableDTO timeTableDTO) {
        return "timetables/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("timetable") @Valid TimeTableDTO timeTableDTO, BindingResult bindingResult) {
        System.out.println(timeTableDTO.toString());
        if (bindingResult.hasErrors())
            return "timetables/new";
        timeTableService.insertTimeTable(timeTableDTO);
        return "redirect:/timeTables";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("timetable", timeTableService.selectTimeTable(id));
        return "timetables/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("timetable") @Valid TimeTableDTO timeTableDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "timetables/edit";
        timeTableService.updateTimeTable(timeTableDTO);
        return "redirect:/timeTables";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        timeTableService.deleteTimeTable(id);
        return "redirect:/timeTables";
    }
}
