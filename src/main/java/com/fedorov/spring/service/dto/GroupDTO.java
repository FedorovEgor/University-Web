package com.fedorov.spring.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class GroupDTO {
    private Integer groupId;
    @NotEmpty(message = "Group name should not be empty.")
    @Size(min = 2, max = 10, message = "Group name should be between 2 and 10 symbols.")
    private String groupName;

    public GroupDTO() {
    }

    public GroupDTO(String groupName) {
        this.groupName = groupName;
    }

    public GroupDTO(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupDTO groupDTO = (GroupDTO) o;
        return groupId == groupDTO.groupId &&
                Objects.equals(groupName, groupDTO.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName);
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + "'}";
    }
}

