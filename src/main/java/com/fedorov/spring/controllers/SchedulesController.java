package com.fedorov.spring.controllers;

import com.fedorov.spring.service.*;
import com.fedorov.spring.service.dto.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/schedules")
public class SchedulesController {

    private final ScheduleService scheduleService;
    private final GroupService groupService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final TimeTableService timeTableService;

    @Autowired
    public SchedulesController(ScheduleService scheduleService, GroupService groupService,
                               CourseService courseService, TeacherService teacherService,
                               TimeTableService timeTableService) {
        this.scheduleService = scheduleService;
        this.groupService = groupService;
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.timeTableService = timeTableService;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("schedules", scheduleService.selectAllSchedules());
        return "schedules/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("schedule", scheduleService.selectSchedule(id));
        return "schedules/show";
    }

    @GetMapping("/new")
    public String newSchedule(Model model, @ModelAttribute("schedule") ScheduleDTO scheduleDTO) {
        model.addAttribute("groups", groupService.selectAllGroups());
        model.addAttribute("courses", courseService.selectAllCourses());
        model.addAttribute("teachers", teacherService.selectAllTeachers());
        model.addAttribute("timetables", timeTableService.selectAllTimeTables());
        return "schedules/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("schedule") @Valid ScheduleDTO scheduleDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "schedules/new";

        scheduleService.insertSchedule(scheduleDTO);
        return "redirect:/schedules";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("schedule", scheduleService.selectSchedule(id));
        model.addAttribute("groups", groupService.selectAllGroups());
        model.addAttribute("courses", courseService.selectAllCourses());
        model.addAttribute("teachers", teacherService.selectAllTeachers());
        model.addAttribute("timetables", timeTableService.selectAllTimeTables());
        return "schedules/edit";
    }

    @PatchMapping("/{scheduleId}")
    public String update(@ModelAttribute("schedule") @Valid ScheduleDTO scheduleDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "schedules/edit";
        scheduleService.updateSchedule(scheduleDTO);
        return "redirect:/schedules";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        scheduleService.deleteSchedule(id);
        return "redirect:/schedules";
    }
}
