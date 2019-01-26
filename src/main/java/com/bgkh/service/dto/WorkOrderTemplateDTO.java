package com.bgkh.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.bgkh.domain.enumeration.WorkOrderTemplateType;
import com.bgkh.domain.enumeration.WorkOrderTemplateFunctionType;

/**
 * A DTO for the WorkOrderTemplate entity.
 */
public class WorkOrderTemplateDTO implements Serializable {

    private Long id;

    private Long numberOfDays;

    private Long hoursOfUsage;

    private String description;

    private Long dueDays;

    @NotNull
    private WorkOrderTemplateType workOrderType;

    @NotNull
    private WorkOrderTemplateFunctionType functionType;

    @NotNull
    private String name;

    private String shortDesc;


    private Long assetSpecificationTypeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Long numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
    public Long getHoursOfUsage() {
        return hoursOfUsage;
    }

    public void setHoursOfUsage(Long hoursOfUsage) {
        this.hoursOfUsage = hoursOfUsage;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Long getDueDays() {
        return dueDays;
    }

    public void setDueDays(Long dueDays) {
        this.dueDays = dueDays;
    }
    public WorkOrderTemplateType getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(WorkOrderTemplateType workOrderType) {
        this.workOrderType = workOrderType;
    }
    public WorkOrderTemplateFunctionType getFunctionType() {
        return functionType;
    }

    public void setFunctionType(WorkOrderTemplateFunctionType functionType) {
        this.functionType = functionType;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public Long getAssetSpecificationTypeId() {
        return assetSpecificationTypeId;
    }

    public void setAssetSpecificationTypeId(Long assetSpecificationTypeId) {
        this.assetSpecificationTypeId = assetSpecificationTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkOrderTemplateDTO workOrderTemplateDTO = (WorkOrderTemplateDTO) o;

        if ( ! Objects.equals(id, workOrderTemplateDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkOrderTemplateDTO{" +
            "id=" + id +
            ", numberOfDays='" + numberOfDays + "'" +
            ", hoursOfUsage='" + hoursOfUsage + "'" +
            ", description='" + description + "'" +
            ", dueDays='" + dueDays + "'" +
            ", workOrderType='" + workOrderType + "'" +
            ", functionType='" + functionType + "'" +
            ", name='" + name + "'" +
            ", shortDesc='" + shortDesc + "'" +
            '}';
    }
}
