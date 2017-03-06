package com.bgkh.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the AssetSpecificationTypeField entity.
 */
public class AssetSpecificationTypeFieldDTO implements Serializable {

    private Long id;

    @NotNull
    private String fieldLable;

    @NotNull
    private String fieldName;

    @NotNull
    private String fieldType;


    private Long assetSpecificationTypeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
            ", fieldLable='" + fieldLable + "'" +
            ", fieldName='" + fieldName + "'" +
            ", fieldType='" + fieldType + "'" +
            '}';
    }
}
