package com.fedorov.spring.api;

import com.fedorov.spring.service.GroupService;
import com.fedorov.spring.service.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupsRestController {

    private GroupService groupService;

    @Autowired
    public GroupsRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public List<GroupDTO> getAllGroups() {
        return groupService.selectAllGroups();
    }

    @GetMapping("/single/{id}")
    public GroupDTO getGroupById(@PathVariable int id) {
        return groupService.selectGroup(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGroup(@RequestBody GroupDTO groupDTO) {
        groupService.insertGroup(groupDTO);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateGroup(@PathVariable int id, @RequestBody GroupDTO groupDTO) {
        if (groupService.selectGroup(id) != null)
            groupService.updateGroup(groupDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@PathVariable int id) {
        if (groupService.selectGroup(id) != null)
            groupService.deleteGroup(id);
    }
}

