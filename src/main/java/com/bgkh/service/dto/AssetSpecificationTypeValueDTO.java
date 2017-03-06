package com.bgkh.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the AssetSpecificationTypeValue entity.
 */
public class AssetSpecificationTypeValueDTO implements Serializable {

    private Long id;

    @NotNull
    private String fieldValue;


    private Long assetSpecificationTypeFieldId;
    
    private Long assetId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Long getAssetSpecificationTypeFieldId() {
        return assetSpecificationTypeFieldId;
    }

    public void setAssetSpecificationTypeFieldId(Long assetSpecificationTypeFieldId) {
        this.assetSpecificationTypeFieldId = assetSpecificationTypeFieldId;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetSpecificationTypeValueDTO assetSpecificationTypeValueDTO = (AssetSpecificationTypeValueDTO) o;

        if ( ! Objects.equals(id, assetSpecificationTypeValueDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetSpecificationTypeValueDTO{" +
            "id=" + id +
            ", fieldValue='" + fieldValue + "'" +
            '}';
    }
}
