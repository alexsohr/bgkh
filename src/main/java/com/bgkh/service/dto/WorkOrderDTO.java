package com.bgkh.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the WorkOrder entity.
 */
public class WorkOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean track;

    private String description;

    private LocalDate trackDate;


    private Long assetId;

    private Long workOrderTemplateId;

    private WorkOrderTemplateDTO workOrderTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getTrack() {
        return track;
    }

    public void setTrack(Boolean track) {
        this.track = track;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getTrackDate() {
        return trackDate;
    }

    public void setTrackDate(LocalDate trackDate) {
        this.trackDate = trackDate;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getWorkOrderTemplateId() {
        return workOrderTemplateId;
    }

    public void setWorkOrderTemplateId(Long workOrderTemplateId) {
        this.workOrderTemplateId = workOrderTemplateId;
    }


    public WorkOrderTemplateDTO getWorkOrderTemplate() {
        return workOrderTemplate;
    }

    public void setWorkOrderTemplate(WorkOrderTemplateDTO workOrderTemplate) {
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
            ", track=" + track +
            ", description='" + description + '\'' +
            ", trackDate=" + trackDate +
            ", assetId=" + assetId +
            ", workOrderTemplateId=" + workOrderTemplateId +
            ", workOrderTemplate=" + workOrderTemplate +
            '}';
    }
}
