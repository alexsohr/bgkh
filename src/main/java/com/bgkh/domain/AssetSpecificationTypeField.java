package com.bgkh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AssetSpecificationTypeField.
 */
@Entity
@Table(name = "asset_specification_type_field")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AssetSpecificationTypeField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "field_label", nullable = false)
    private String fieldLabel;

    @NotNull
    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @NotNull
    @Column(name = "field_type", nullable = false)
    private String fieldType;

    @Column(name = "capacity_unit")
    private String capacityUnit;

    @ManyToOne
    private AssetSpecificationType assetSpecificationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
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

    public String getCapacityUnit() {
        return capacityUnit;
    }

    public void setCapacityUnit(String capacityUnit) {
        this.capacityUnit = capacityUnit;
    }

    public AssetSpecificationType getAssetSpecificationType() {
        return assetSpecificationType;
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
        AssetSpecificationTypeField assetSpecificationTypeField = (AssetSpecificationTypeField) o;
        if (assetSpecificationTypeField.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, assetSpecificationTypeField.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetSpecificationTypeField{" +
            "id=" + id +
            ", fieldLabel='" + fieldLabel + "'" +
            ", fieldName='" + fieldName + "'" +
            ", fieldType='" + fieldType + "'" +
            ", capacityUnit='" + capacityUnit + "'" +
            '}';
    }
}
