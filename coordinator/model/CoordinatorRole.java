package com.myinappbilling.coordinator.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a role assigned to a coordinator within the system.
 */
public class CoordinatorRole implements Serializable {

    private String roleId;
    private String roleName;
    private String description;
    private boolean isActive;
    private int level;

    public CoordinatorRole() {
    }

    public CoordinatorRole(String roleId, String roleName, String description, boolean isActive, int level) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.description = description;
        this.isActive = isActive;
        this.level = level;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinatorRole that = (CoordinatorRole) o;
        return Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }

    @Override
    public String toString() {
        return "CoordinatorRole{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", level=" + level +
                '}';
    }
}
