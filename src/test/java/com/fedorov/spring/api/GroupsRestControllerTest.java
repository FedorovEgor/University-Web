package com.fedorov.spring.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedorov.spring.service.GroupService;
import com.fedorov.spring.service.dto.GroupDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(GroupsRestController.class)
public class GroupsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupService groupService;

    @Test
    public void selectAllGroups_allGroupsExpected() throws Exception {
        List<GroupDTO> expectedGroups = Arrays.asList(
                new GroupDTO(1, "ed-48"),
                new GroupDTO(2, "ef-75")
        );

        Mockito.when(groupService.selectAllGroups()).thenReturn(expectedGroups);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/groups/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedGroups);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(groupService).selectAllGroups();
    }

    @Test
    public void selectSingleGroup_singleGroupIsExpected() throws Exception {
        GroupDTO expectedGroup = new GroupDTO(1, "ed-48");

        Mockito.when(groupService.selectGroup(1)).thenReturn(expectedGroup);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/groups/single/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedGroup);

        assertEquals(actualJsonResponse, expectedJsonResponse);
        Mockito.verify(groupService).selectGroup(1);
    }

    @Test
    public void addGroup_newGroupExpectedToBeAdded() throws Exception {
        GroupDTO savedGroupDTO = new GroupDTO(1, "ed-48");

        groupService.insertGroup(savedGroupDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/groups/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(savedGroupDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateGroup_groupExpectedToBeUpdated() throws Exception {
        GroupDTO groupDTOThatWillBeUpdated = new GroupDTO(2, "ed-48");

        mockMvc.perform(MockMvcRequestBuilders.put("/groups/update/2")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(groupDTOThatWillBeUpdated)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteGroup_groupExpectedToBeDeleted() throws Exception {
        Mockito.when(groupService.deleteGroup(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/groups/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

