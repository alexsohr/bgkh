package com.bgkh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.bgkh.domain.enumeration.WorkOrderStatus;

import com.bgkh.domain.enumeration.WorkOrderType;

import com.bgkh.domain.enumeration.WorkOrderPriority;

import com.bgkh.domain.enumeration.WorkOrderEstStatus;

/**
 * A WorkOrder.
 */
@Entity
@Table(name = "work_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "work_order_status", nullable = false)
    private WorkOrderStatus workOrderStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "work_order_type", nullable = false)
    private WorkOrderType workOrderType;

    @Size(min = 10, max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @NotNull
    @Column(name = "date_completed", nullable = false)
    private LocalDate dateCompleted;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private WorkOrderPriority priority;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "est", nullable = false)
    private WorkOrderEstStatus est;

    @Column(name = "estimated_hours")
    private Long estimatedHours;

    @Size(min = 10, max = 500)
    @Column(name = "comments", length = 500)
    private String comments;

    @NotNull
    @Column(name = "tracker", nullable = false)
    private Boolean tracker;

    @ManyToOne
    private Asset asset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkOrderStatus getWorkOrderStatus() {
        return workOrderStatus;
    }

    public WorkOrder workOrderStatus(WorkOrderStatus workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
        return this;
    }

    public void setWorkOrderStatus(WorkOrderStatus workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public WorkOrderType getWorkOrderType() {
        return workOrderType;
    }

    public WorkOrder workOrderType(WorkOrderType workOrderType) {
        this.workOrderType = workOrderType;
        return this;
    }

    public void setWorkOrderType(WorkOrderType workOrderType) {
        this.workOrderType = workOrderType;
    }

    public String getDescription() {
        return description;
    }

    public WorkOrder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCompleted() {
        return dateCompleted;
    }

    public WorkOrder dateCompleted(LocalDate dateCompleted) {
        this.dateCompleted = dateCompleted;
        return this;
    }

    public void setDateCompleted(LocalDate dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public WorkOrderPriority getPriority() {
        return priority;
    }

    public WorkOrder priority(WorkOrderPriority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(WorkOrderPriority priority) {
        this.priority = priority;
    }

    public WorkOrderEstStatus getEst() {
        return est;
    }

    public WorkOrder est(WorkOrderEstStatus est) {
        this.est = est;
        return this;
    }

    public void setEst(WorkOrderEstStatus est) {
        this.est = est;
    }

    public Long getEstimatedHours() {
        return estimatedHours;
    }

    public WorkOrder estimatedHours(Long estimatedHours) {
        this.estimatedHours = estimatedHours;
        return this;
    }

    public void setEstimatedHours(Long estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getComments() {
        return comments;
    }

    public WorkOrder comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean isTracker() {
        return tracker;
    }

    public WorkOrder tracker(Boolean tracker) {
        this.tracker = tracker;
        return this;
    }

    public void setTracker(Boolean tracker) {
        this.tracker = tracker;
    }

    public Asset getAsset() {
        return asset;
    }

    public WorkOrder asset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkOrder workOrder = (WorkOrder) o;
        if (workOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
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
