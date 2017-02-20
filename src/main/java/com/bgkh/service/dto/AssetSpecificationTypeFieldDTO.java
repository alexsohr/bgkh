package com.bgkh.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.bgkh.domain.enumeration.AssetSpecificationType;

/**
 * A DTO for the AssetSpecificationTypeField entity.
 */
public class AssetSpecificationTypeFieldDTO implements Serializable {

    private Long id;

    @NotNull
    private AssetSpecificationType assetSpecificationType;

    @NotNull
    private String fieldLable;

    @NotNull
    private String fieldName;

    @NotNull
    private String fieldType;


    private Long assetSpecificationTypeFieldId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public AssetSpecificationType getAssetSpecificationType() {
        return assetSpecificationType;
    }

    public void setAssetSpecificationType(AssetSpecificationType assetSpecificationType) {
        this.assetSpecificationType = assetSpecificationType;
    }
    public String getFieldLable() {
        return fieldLable;
    }

    public void setFieldLable(String fieldLable) {
        this.fieldLable = fieldLable;
    }
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Long getAssetSpecificationTypeFieldId() {
        return assetSpecificationTypeFieldId;
    }

    public void setAssetSpecificationTypeFieldId(Long assetId) {
        this.assetSpecificationTypeFieldId = assetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO = (AssetSpecificationTypeFieldDTO) o;

        if ( ! Objects.equals(id, assetSpecificationTypeFieldDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetSpecificationTypeFieldDTO{" +
            "id=" + id +
            ", assetSpecificationType='" + assetSpecificationType + "'" +
            ", fieldLable='" + fieldLable + "'" +
            ", fieldName='" + fieldName + "'" +
            ", fieldType='" + fieldType + "'" +
            '}';
    }
}
