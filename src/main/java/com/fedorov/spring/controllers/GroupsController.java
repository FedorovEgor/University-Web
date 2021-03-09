package com.fedorov.spring.controllers;

import com.fedorov.spring.service.GroupService;
import com.fedorov.spring.service.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/groups")
public class GroupsController {

    private final GroupService groupService;

    @Autowired
    public GroupsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("groups", groupService.selectAllGroups());
        return "groups/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("group", groupService.selectGroup(id));
        return "groups/show";
    }

    @GetMapping("/new")
    public String newGroup(@ModelAttribute("group") GroupDTO groupDTO) {
        return "groups/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("group") @Valid GroupDTO groupDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "groups/new";
        groupService.insertGroup(groupDTO);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("group", groupService.selectGroup(id));
        return "groups/edit";
    }

    @PatchMapping("/{groupId}")
    public String update(@ModelAttribute("group") @Valid GroupDTO groupDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "groups/edit";
        groupService.updateGroup(groupDTO);
        return "redirect:/groups";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        groupService.deleteGroup(id);
        return "redirect:/groups";
    }
}
