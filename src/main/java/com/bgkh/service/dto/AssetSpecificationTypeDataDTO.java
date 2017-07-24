package com.bgkh.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by alex on 3/8/17.
 */
public class AssetSpecificationTypeDataDTO implements Serializable {

    private Long assetId;

    private Long assetSpecificationTypeFieldId;

    private String fieldValue;

    private String fieldLabel;

    private String capacityUnit;

    @NotNull
    private String fieldType;

    private Long fieldId;

    private Long valueId;

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getAssetSpecificationTypeFieldId() {
        return assetSpecificationTypeFieldId;
    }

    public void setAssetSpecificationTypeFieldId(Long assetSpecificationTypeFieldId) {
        this.assetSpecificationTypeFieldId = assetSpecificationTypeFieldId;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }

    public String getCapacityUnit() {
        return capacityUnit;
    }

    public void setCapacityUnit(String capacityUnit) {
        this.capacityUnit = capacityUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetSpecificationTypeDataDTO that = (AssetSpecificationTypeDataDTO) o;


        if (fieldId != null ? !fieldId.equals(that.fieldId) : that.fieldId != null) return false;
        return valueId != null ? valueId.equals(that.valueId) : that.valueId == null;
    }

    @Override
    public int hashCode() {
        int result = assetId != null ? assetId.hashCode() : 0;
        result = 31 * result + (assetSpecificationTypeFieldId != null ? assetSpecificationTypeFieldId.hashCode() : 0);
        result = 31 * result + (fieldValue != null ? fieldValue.hashCode() : 0);
        result = 31 * result + (fieldLabel != null ? fieldLabel.hashCode() : 0);
        result = 31 * result + (capacityUnit != null ? capacityUnit.hashCode() : 0);
        result = 31 * result + (fieldType != null ? fieldType.hashCode() : 0);
        result = 31 * result + (fieldId != null ? fieldId.hashCode() : 0);
        result = 31 * result + (valueId != null ? valueId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AssetSpecificationTypeDataDTO{" +
            "assetId=" + assetId +
            ", assetSpecificationTypeFieldId=" + assetSpecificationTypeFieldId +
            ", fieldValue='" + fieldValue + '\'' +
            ", fieldLabel='" + fieldLabel + '\'' +
            ", capacityUnit='" + capacityUnit + '\'' +
            ", fieldType='" + fieldType + '\'' +
            ", fieldId=" + fieldId +
            ", valueId=" + valueId +
            '}';
    }
}
