package com.bgkh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.bgkh.domain.enumeration.ScheduleStatus;

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
    private LocalDate createDate;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_status")
    private ScheduleStatus scheduleStatus;

    @ManyToOne
    private WorkOrder workOrder;

    @OneToMany(mappedBy = "workOrderSchedule")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkOrderHistory> workOrderHistories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public WorkOrderSchedule createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public WorkOrderSchedule expireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
        return this;
    }

    public void setExpireDate(LocalDate expireDate) {
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
            '}';
    }
}
