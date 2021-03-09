package com.fedorov.spring.api;

import com.fedorov.spring.service.CourseService;
import com.fedorov.spring.service.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CoursesRestController {

    private CourseService courseService;

    @Autowired
    public CoursesRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public List<CourseDTO> getAllCourses() {
        return courseService.selectAllCourses();
    }

    @GetMapping("/single/{id}")
    public CourseDTO getCourseById(@PathVariable int id) {
        return courseService.selectCourse(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCourse(@RequestBody CourseDTO courseDTO) {
        courseService.insertCourse(courseDTO);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCourse(@PathVariable int id, @RequestBody CourseDTO courseDTO) {
        if (courseService.selectCourse(id) != null)
            courseService.updateCourse(courseDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCourse(@PathVariable int id) {
        if (courseService.selectCourse(id) != null)
            courseService.deleteCourse(id);
    }
}

