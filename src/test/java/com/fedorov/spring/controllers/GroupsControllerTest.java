package com.fedorov.spring.controllers;

import com.fedorov.spring.service.GroupService;
import com.fedorov.spring.service.dto.GroupDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
public class GroupsControllerTest {

    private MockMvc mockMvc;

    @Mock
    GroupService groupService;

    @InjectMocks
    GroupsController groupsController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupsController).build();
    }

    @Test
    public void selectAllGroups_allGroupsPageShown() throws Exception {
        List<GroupDTO> expectedGroups = Arrays.asList(new GroupDTO(1,"fk-44"));

        when(groupService.selectAllGroups()).thenReturn(expectedGroups);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/groups"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("groups/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("groups"))
                .andExpect(MockMvcResultMatchers.model().attribute("groups", expectedGroups));

        verify(groupService).selectAllGroups();
    }

    @Test
    public void selectGroupById_singleGroupPageShown() throws Exception {
        GroupDTO expectedGroup = new GroupDTO(1,"fk-44");

        when(groupService.selectGroup(1)).thenReturn(expectedGroup);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/groups/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("groups/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("group"))
                .andExpect(MockMvcResultMatchers.model().attribute("group", expectedGroup));

        verify(groupService).selectGroup(1);
    }

    @Test
    public void newGroup_newGroupPageShown() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/groups/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("groups/new"));
    }

    @Test
    public void createGroup_newGroupCreatedAndPageRedirected() throws Exception {
        GroupDTO expectedGroup = new GroupDTO(2,"fk-44");

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/groups")
                        .param("groupId", "2")
                        .param("groupName", "fk-44"))
                .andExpect(redirectedUrl("/groups"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("group"))
                .andExpect(MockMvcResultMatchers.model().attribute("group", expectedGroup));
    }

    @Test
    public void editGroup_editPageShown() throws Exception {
        GroupDTO expectedGroup = new GroupDTO(1, "lm-41");

        when(groupService.selectGroup(1)).thenReturn(expectedGroup);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/groups/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("groups/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("group"))
                .andExpect(MockMvcResultMatchers.model().attribute("group", expectedGroup));

        verify(groupService).selectGroup(1);
    }

    @Test
    public void updateGroup_groupUpdatedAndPageRedirected() throws Exception {
        GroupDTO expectedGroup = new GroupDTO(1,"mk-07");
        boolean isUpdated;
        when(groupService.updateGroup(expectedGroup)).thenReturn(isUpdated = true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.patch("/groups/1")
                        .param("groupId", "1")
                        .param("groupName", "mk-07"))
                .andExpect(redirectedUrl("/groups"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("group"))
                .andExpect(MockMvcResultMatchers.model().attribute("group", expectedGroup));

        assertTrue(isUpdated);
        verify(groupService).updateGroup(expectedGroup);
    }

    @Test
    public void deleteGroup_groupDeletedAndPageRedirected() throws Exception {
        boolean isDeleted;
        when(groupService.deleteGroup(1)).thenReturn(isDeleted = true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/groups/1"))
                .andExpect(redirectedUrl("/groups"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/groups"));

        assertTrue(isDeleted);
        verify(groupService).deleteGroup(1);
    }
}
