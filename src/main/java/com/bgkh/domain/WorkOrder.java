package com.bgkh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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
    @Column(name = "track", nullable = false)
    private Boolean track;

    @Column(name = "description")
    private String description;

    @Column(name = "track_date")
    private LocalDate trackDate;

    @ManyToOne
    @NotNull
    private Asset asset;

    @ManyToOne
    @NotNull
    private WorkOrderTemplate workOrderTemplate;

    @OneToMany(mappedBy = "workOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkOrderSchedule> workOrderSchedules = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isTrack() {
        return track;
    }

    public WorkOrder track(Boolean track) {
        this.track = track;
        return this;
    }

    public void setTrack(Boolean track) {
        this.track = track;
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

    public LocalDate getTrackDate() {
        return trackDate;
    }

    public WorkOrder trackDate(LocalDate trackDate) {
        this.trackDate = trackDate;
        return this;
    }

    public void setTrackDate(LocalDate trackDate) {
        this.trackDate = trackDate;
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

    public WorkOrderTemplate getWorkOrderTemplate() {
        return workOrderTemplate;
    }

    public WorkOrder workOrderTemplate(WorkOrderTemplate workOrderTemplate) {
        this.workOrderTemplate = workOrderTemplate;
        return this;
    }

    public void setWorkOrderTemplate(WorkOrderTemplate workOrderTemplate) {
        this.workOrderTemplate = workOrderTemplate;
    }

    public Set<WorkOrderSchedule> getWorkOrderSchedules() {
        return workOrderSchedules;
    }

    public WorkOrder workOrderSchedules(Set<WorkOrderSchedule> workOrderSchedules) {
        this.workOrderSchedules = workOrderSchedules;
        return this;
    }

    public WorkOrder addWorkOrderSchedule(WorkOrderSchedule workOrderSchedule) {
        workOrderSchedules.add(workOrderSchedule);
        workOrderSchedule.setWorkOrder(this);
        return this;
    }

    public WorkOrder removeWorkOrderSchedule(WorkOrderSchedule workOrderSchedule) {
        workOrderSchedules.remove(workOrderSchedule);
        workOrderSchedule.setWorkOrder(null);
        return this;
    }

    public void setWorkOrderSchedules(Set<WorkOrderSchedule> workOrderSchedules) {
        this.workOrderSchedules = workOrderSchedules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkOrder workOrder = (WorkOrder) o;

        if (!asset.equals(workOrder.asset)) return false;
        return workOrderTemplate.equals(workOrder.workOrderTemplate);
    }

    @Override
    public int hashCode() {
        int result = asset.hashCode();
        result = 31 * result + workOrderTemplate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
            "id=" + id +
            ", track=" + track +
            ", description='" + description + '\'' +
            ", trackDate=" + trackDate +
            ", asset=" + asset +
            ", workOrderTemplate=" + workOrderTemplate +
            '}';
    }
}
