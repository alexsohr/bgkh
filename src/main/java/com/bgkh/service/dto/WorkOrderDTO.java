package com.bgkh.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.bgkh.domain.enumeration.WorkOrderStatus;
import com.bgkh.domain.enumeration.WorkOrderType;
import com.bgkh.domain.enumeration.WorkOrderPriority;
import com.bgkh.domain.enumeration.WorkOrderEstStatus;

/**
 * A DTO for the WorkOrder entity.
 */
public class WorkOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private WorkOrderStatus workOrderStatus;

    @NotNull
    private WorkOrderType workOrderType;

    @Size(min = 10, max = 500)
    private String description;

    @NotNull
    private LocalDate dateCompleted;

    @NotNull
    private WorkOrderPriority priority;

    @NotNull
    private WorkOrderEstStatus est;

    private Long estimatedHours;

    @Size(min = 10, max = 500)
    private String comments;

    @NotNull
    private Boolean tracker;


    private Long assetId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public WorkOrderStatus getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(WorkOrderStatus workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }
    public WorkOrderType getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(WorkOrderType workOrderType) {
        this.workOrderType = workOrderType;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDate dateCompleted) {
        this.dateCompleted = dateCompleted;
    }
    public WorkOrderPriority getPriority() {
        return priority;
    }

    public void setPriority(WorkOrderPriority priority) {
        this.priority = priority;
    }
    public WorkOrderEstStatus getEst() {
        return est;
    }

    public void setEst(WorkOrderEstStatus est) {
        this.est = est;
    }
    public Long getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Long estimatedHours) {
        this.estimatedHours = estimatedHours;
    }
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    public Boolean getTracker() {
        return tracker;
    }

    public void setTracker(Boolean tracker) {
        this.tracker = tracker;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkOrderDTO workOrderDTO = (WorkOrderDTO) o;

        if ( ! Objects.equals(id, workOrderDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkOrderDTO{" +
            "id=" + id +
            ", workOrderStatus='" + workOrderStatus + "'" +
            ", workOrderType='" + workOrderType + "'" +
            ", description='" + description + "'" +
            ", dateCompleted='" + dateCompleted + "'" +
            ", priority='" + priority + "'" +
            ", est='" + est + "'" +
            ", estimatedHours='" + estimatedHours + "'" +
            ", comments='" + comments + "'" +
            ", tracker='" + tracker + "'" +
            '}';
    }
}
