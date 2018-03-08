package com.bgkh.domain;

import com.bgkh.domain.enumeration.ScheduleStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A WorkOrderSchedule.
 */
@Entity
@Table(name = "work_order_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkOrderSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "create_date")
    private ZonedDateTime createDate = ZonedDateTime.now();

    @Column(name = "expire_date")
    private ZonedDateTime expireDate = ZonedDateTime.now();

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_status")
    private ScheduleStatus scheduleStatus;

    @Column(name = "completed_date")
    private ZonedDateTime completedDate;

    @ManyToOne
    private WorkOrder workOrder;

    @OneToMany(mappedBy = "workOrderSchedule")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkOrderHistory> workOrderHistories = new HashSet<>();

    @ManyToOne
    @NotNull
    private Asset asset;

    @ManyToOne
    @NotNull
    private WorkOrderTemplate workOrderTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public WorkOrderSchedule createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getExpireDate() {
        return expireDate;
    }

    public WorkOrderSchedule expireDate(ZonedDateTime expireDate) {
        this.expireDate = expireDate;
        return this;
    }

    public void setExpireDate(ZonedDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public String getDescription() {
        return description;
    }

    public WorkOrderSchedule description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ScheduleStatus getScheduleStatus() {
        return scheduleStatus;
    }

    public WorkOrderSchedule scheduleStatus(ScheduleStatus scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
        return this;
    }

    public void setScheduleStatus(ScheduleStatus scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public ZonedDateTime getCompletedDate() {
        return completedDate;
    }

    public WorkOrderSchedule completedDate(ZonedDateTime completedDate) {
        this.completedDate = completedDate;
        return this;
    }

    public void setCompletedDate(ZonedDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public WorkOrderSchedule workOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
        return this;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public Set<WorkOrderHistory> getWorkOrderHistories() {
        return workOrderHistories;
    }

    public WorkOrderSchedule workOrderHistories(Set<WorkOrderHistory> workOrderHistories) {
        this.workOrderHistories = workOrderHistories;
        return this;
    }

    public WorkOrderSchedule addWorkOrderHistory(WorkOrderHistory workOrderHistory) {
        workOrderHistories.add(workOrderHistory);
        workOrderHistory.setWorkOrderSchedule(this);
        return this;
    }

    public WorkOrderSchedule removeWorkOrderHistory(WorkOrderHistory workOrderHistory) {
        workOrderHistories.remove(workOrderHistory);
        workOrderHistory.setWorkOrderSchedule(null);
        return this;
    }

    public void setWorkOrderHistories(Set<WorkOrderHistory> workOrderHistories) {
        this.workOrderHistories = workOrderHistories;
    }

    public Asset getAsset() {
        return asset;
    }

    public WorkOrderSchedule asset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public WorkOrderTemplate getWorkOrderTemplate() {
        return workOrderTemplate;
    }

    public WorkOrderSchedule workOrderTemplate(WorkOrderTemplate workOrderTemplate) {
        this.workOrderTemplate = workOrderTemplate;
        return this;
    }

    public void setWorkOrderTemplate(WorkOrderTemplate workOrderTemplate) {
        this.workOrderTemplate = workOrderTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkOrderSchedule workOrderSchedule = (WorkOrderSchedule) o;
        if (workOrderSchedule.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workOrderSchedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkOrderSchedule{" +
            "id=" + id +
            ", createDate='" + createDate + "'" +
            ", expireDate='" + expireDate + "'" +
            ", description='" + description + "'" +
            ", scheduleStatus='" + scheduleStatus + "'" +
            ", completedDate='" + completedDate + "'" +
            '}';
    }
}
