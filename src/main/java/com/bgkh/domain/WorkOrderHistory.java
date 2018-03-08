package com.bgkh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.bgkh.domain.enumeration.HistoryStatus;

/**
 * A WorkOrderHistory.
 */
@Entity
@Table(name = "work_order_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkOrderHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "create_date")
    private ZonedDateTime createDate = ZonedDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "history_status")
    private HistoryStatus historyStatus;

    @NotNull
    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne
    private WorkOrderSchedule workOrderSchedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public WorkOrderHistory createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public HistoryStatus getHistoryStatus() {
        return historyStatus;
    }

    public WorkOrderHistory historyStatus(HistoryStatus historyStatus) {
        this.historyStatus = historyStatus;
        return this;
    }

    public void setHistoryStatus(HistoryStatus historyStatus) {
        this.historyStatus = historyStatus;
    }

    public String getComment() {
        return comment;
    }

    public WorkOrderHistory comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public WorkOrderSchedule getWorkOrderSchedule() {
        return workOrderSchedule;
    }

    public WorkOrderHistory workOrderSchedule(WorkOrderSchedule workOrderSchedule) {
        this.workOrderSchedule = workOrderSchedule;
        return this;
    }

    public void setWorkOrderSchedule(WorkOrderSchedule workOrderSchedule) {
        this.workOrderSchedule = workOrderSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkOrderHistory workOrderHistory = (WorkOrderHistory) o;
        if (workOrderHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workOrderHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkOrderHistory{" +
            "id=" + id +
            ", createDate='" + createDate + "'" +
            ", historyStatus='" + historyStatus + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
