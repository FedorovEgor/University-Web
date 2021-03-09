package com.fedorov.spring.api;

import com.fedorov.spring.service.StudentService;
import com.fedorov.spring.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsRestController {

    private StudentService studentService;

    @Autowired
    public StudentsRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public List<StudentDTO> getAllStudents() {
        return studentService.selectAllStudents();
    }

    @GetMapping("/single/{id}")
    public StudentDTO getStudentById(@PathVariable int id) {
        return studentService.selectStudent(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStudent(@RequestBody StudentDTO studentDTO) {
        studentService.insertStudent(studentDTO);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStudent(@PathVariable int id, @RequestBody StudentDTO studentDTO) {
        if (studentService.selectStudent(id) != null)
            studentService.updateStudent(studentDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable int id) {
        if (studentService.selectStudent(id) != null)
            studentService.deleteStudent(id);
    }
}

