package com.fedorov.spring.controllers;

import com.fedorov.spring.service.CourseService;
import com.fedorov.spring.service.TeacherService;
import com.fedorov.spring.service.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    @Autowired
    public CoursesController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("courses", courseService.selectAllCourses());
        return "courses/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("course", courseService.selectCourse(id));
        return "courses/show";
    }

    @GetMapping("/new")
    public String newCourse(Model model, @ModelAttribute("course")CourseDTO courseDTO) {
        model.addAttribute("teachers", teacherService.selectAllTeachers());
        return "courses/new";
    }

    @PostMapping
    public String create(@ModelAttribute("course") @Valid CourseDTO courseDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "courses/new";
        courseService.insertCourse(courseDTO);
        return "redirect:/courses";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("course", courseService.selectCourse(id));
        model.addAttribute("teachers", teacherService.selectAllTeachers());
        return "courses/edit";
    }

    @PatchMapping("/{courseId}")
    public String update(@ModelAttribute("course") @Valid CourseDTO courseDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "courses/edit";
        courseService.updateCourse(courseDTO);
        return "redirect:/courses";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
}
