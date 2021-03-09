package com.fedorov.spring.api;

import com.fedorov.spring.service.TeacherService;
import com.fedorov.spring.service.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeachersRestController {

    private TeacherService teacherService;

    @Autowired
    public TeachersRestController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/all")
    public List<TeacherDTO> getAllTeachers() {
        return teacherService.selectAllTeachers();
    }

    @GetMapping("/single/{id}")
    public TeacherDTO getSingleTeacher(@PathVariable int id) {
        return teacherService.selectTeacher(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTeacher(@RequestBody TeacherDTO teacherDTO) {
        teacherService.insertTeacher(teacherDTO);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTeacher(@PathVariable int id, @RequestBody TeacherDTO teacherDTO) {
        if (teacherService.selectTeacher(id) != null)
            teacherService.updateTeacher(teacherDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeacher(@PathVariable int id) {
        if (teacherService.selectTeacher(id) != null)
            teacherService.deleteTeacher(id);
    }
}

