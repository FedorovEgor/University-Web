package com.fedorov.spring.api;

import com.fedorov.spring.service.TimeTableService;
import com.fedorov.spring.service.dto.TimeTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("timeTables")
public class TimeTablesRestController {

    private TimeTableService timeTableService;

    @Autowired
    public TimeTablesRestController(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    @GetMapping("/all")
    public List<TimeTableDTO> getAllTimeTables() {
        return timeTableService.selectAllTimeTables();
    }

    @GetMapping("/single/{id}")
    public TimeTableDTO getSingleTimeTable(@PathVariable int id) {
        return timeTableService.selectTimeTable(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTimeTable(@RequestBody TimeTableDTO timeTableDTO) {
        timeTableService.insertTimeTable(timeTableDTO);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTimeTable(@PathVariable int id, @RequestBody TimeTableDTO timeTableDTO) {
        if (timeTableService.selectTimeTable(id) != null)
            timeTableService.updateTimeTable(timeTableDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTimeTable(@PathVariable int id) {
        if (timeTableService.selectTimeTable(id) != null)
            timeTableService.deleteTimeTable(id);
    }
}

