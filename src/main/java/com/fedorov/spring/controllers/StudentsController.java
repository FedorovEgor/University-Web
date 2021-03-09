package com.fedorov.spring.controllers;

import com.fedorov.spring.service.GroupService;
import com.fedorov.spring.service.StudentService;
import com.fedorov.spring.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentsController {

    private final StudentService studentService;
    private final GroupService groupService;

    @Autowired
    public StudentsController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping()
    public String index(Model model) {
        List<StudentDTO> studentsList = studentService.selectAllStudents();
        model.addAttribute("students", studentsList);
        return "students/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        StudentDTO studentDTO = studentService.selectStudent(id);
        model.addAttribute("student", studentDTO);
        return "students/show";
    }

    @GetMapping("/new")
    public String newStudent(Model model, @ModelAttribute("student") StudentDTO studentDTO) {
        model.addAttribute("groups", groupService.selectAllGroups());
        return "students/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("student") @Valid StudentDTO studentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "students/new";
        studentService.insertStudent(studentDTO);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("student", studentService.selectStudent(id));
        model.addAttribute("groups", groupService.selectAllGroups());
        return "students/edit";
    }

    @PatchMapping("/{studentId}")
    public String update(@ModelAttribute("student") @Valid StudentDTO studentDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "students/edit";
        studentService.updateStudent(studentDTO);
        return "redirect:/students";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

}
