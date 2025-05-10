package com.myinappbilling.coordinator.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class CoordinatorAssignment implements Serializable {

    private String assignmentId;
    private String coordinatorId;
    private String roleId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String assignedBy;
    private String status;

    public CoordinatorAssignment() {
    }

    public CoordinatorAssignment(String assignmentId, String coordinatorId, String roleId,
                                  LocalDate startDate, LocalDate endDate, String assignedBy, String status) {
        this.assignmentId = assignmentId;
        this.coordinatorId = coordinatorId;
        this.roleId = roleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assignedBy = assignedBy;
        this.status = status;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(String coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return (startDate != null && endDate != null && !today.isBefore(startDate) && !today.isAfter(endDate));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinatorAssignment that = (CoordinatorAssignment) o;
        return Objects.equals(assignmentId, that.assignmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentId);
    }

    @Override
    public String toString() {
        return "CoordinatorAssignment{" +
                "assignmentId='" + assignmentId + '\'' +
                ", coordinatorId='" + coordinatorId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", assignedBy='" + assignedBy + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
