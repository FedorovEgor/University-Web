package com.fedorov.spring.controllers;


import com.fedorov.spring.service.TeacherService;
import com.fedorov.spring.service.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/teachers")
public class TeachersController {

    private final TeacherService teacherService;

    @Autowired
    public TeachersController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("teachers", teacherService.selectAllTeachers());
        return "teachers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("teacher", teacherService.selectTeacher(id));
        return "teachers/show";
    }

    @GetMapping("/new")
    public String newTeacher(@ModelAttribute("teacher") TeacherDTO teacherDTO) {
        return "teachers/new";
    }

    @PostMapping
    public String create(@ModelAttribute("teacher") @Valid TeacherDTO teacherDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "teachers/new";
        teacherService.insertTeacher(teacherDTO);
        return "redirect:/teachers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("teacher", teacherService.selectTeacher(id));
        return "teachers/edit";
    }

    @PatchMapping("/{teacherId}")
    public String update(@ModelAttribute("teacher") @Valid TeacherDTO teacherDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "teachers/edit";
        teacherService.updateTeacher(teacherDTO);
        return "redirect:/teachers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teachers";
    }
}
