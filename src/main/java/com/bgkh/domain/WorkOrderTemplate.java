package com.bgkh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.bgkh.domain.enumeration.WorkOrderTemplateType;

import com.bgkh.domain.enumeration.WorkOrderTemplateFunctionType;

/**
 * A WorkOrderTemplate.
 */
@Entity
@Table(name = "work_order_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkOrderTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number_of_days")
    private Long numberOfDays;

    @Column(name = "hours_of_usage")
    private Long hoursOfUsage;

    @Column(name = "description")
    private String description;

    @Column(name = "due_days")
    private Long dueDays;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "work_order_type", nullable = false)
    private WorkOrderTemplateType workOrderType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "function_type", nullable = false)
    private WorkOrderTemplateFunctionType functionType;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @NotNull
    private AssetSpecificationType assetSpecificationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumberOfDays() {
        return numberOfDays;
    }

    public WorkOrderTemplate numberOfDays(Long numberOfDays) {
        this.numberOfDays = numberOfDays;
        return this;
    }

    public void setNumberOfDays(Long numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Long getHoursOfUsage() {
        return hoursOfUsage;
    }

    public WorkOrderTemplate hoursOfUsage(Long hoursOfUsage) {
        this.hoursOfUsage = hoursOfUsage;
        return this;
    }

    public void setHoursOfUsage(Long hoursOfUsage) {
        this.hoursOfUsage = hoursOfUsage;
    }

    public String getDescription() {
        return description;
    }

    public WorkOrderTemplate description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDueDays() {
        return dueDays;
    }

    public WorkOrderTemplate dueDays(Long dueDays) {
        this.dueDays = dueDays;
        return this;
    }

    public void setDueDays(Long dueDays) {
        this.dueDays = dueDays;
    }

    public WorkOrderTemplateType getWorkOrderType() {
        return workOrderType;
    }

    public WorkOrderTemplate workOrderType(WorkOrderTemplateType workOrderType) {
        this.workOrderType = workOrderType;
        return this;
    }

    public void setWorkOrderType(WorkOrderTemplateType workOrderType) {
        this.workOrderType = workOrderType;
    }

    public WorkOrderTemplateFunctionType getFunctionType() {
        return functionType;
    }

    public WorkOrderTemplate functionType(WorkOrderTemplateFunctionType functionType) {
        this.functionType = functionType;
        return this;
    }

    public void setFunctionType(WorkOrderTemplateFunctionType functionType) {
        this.functionType = functionType;
    }

    public String getName() {
        return name;
    }

    public WorkOrderTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AssetSpecificationType getAssetSpecificationType() {
        return assetSpecificationType;
    }

    public WorkOrderTemplate assetSpecificationType(AssetSpecificationType assetSpecificationType) {
        this.assetSpecificationType = assetSpecificationType;
        return this;
    }

    public void setAssetSpecificationType(AssetSpecificationType assetSpecificationType) {
        this.assetSpecificationType = assetSpecificationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkOrderTemplate workOrderTemplate = (WorkOrderTemplate) o;
        if (workOrderTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workOrderTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkOrderTemplate{" +
            "id=" + id +
            ", numberOfDays='" + numberOfDays + "'" +
            ", hoursOfUsage='" + hoursOfUsage + "'" +
            ", description='" + description + "'" +
            ", dueDays='" + dueDays + "'" +
            ", workOrderType='" + workOrderType + "'" +
            ", functionType='" + functionType + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
