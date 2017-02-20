package com.bgkh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AssetSpecificationTypeValue.
 */
@Entity
@Table(name = "asset_specification_type_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AssetSpecificationTypeValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "field_value", nullable = false)
    private String fieldValue;

    @ManyToOne
    private AssetSpecificationTypeField assetSpecificationTypeField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public AssetSpecificationTypeValue fieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
        return this;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public AssetSpecificationTypeField getAssetSpecificationTypeField() {
        return assetSpecificationTypeField;
    }

    public AssetSpecificationTypeValue assetSpecificationTypeField(AssetSpecificationTypeField assetSpecificationTypeField) {
        this.assetSpecificationTypeField = assetSpecificationTypeField;
        return this;
    }

    public void setAssetSpecificationTypeField(AssetSpecificationTypeField assetSpecificationTypeField) {
        this.assetSpecificationTypeField = assetSpecificationTypeField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssetSpecificationTypeValue assetSpecificationTypeValue = (AssetSpecificationTypeValue) o;
        if (assetSpecificationTypeValue.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, assetSpecificationTypeValue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetSpecificationTypeValue{" +
            "id=" + id +
            ", fieldValue='" + fieldValue + "'" +
            '}';
    }
}
