package com.bgkh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.bgkh.domain.enumeration.AssetSpecificationType;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "asset_specification_type", nullable = false)
    private AssetSpecificationType assetSpecificationType;

    @NotNull
    @Column(name = "field_lable", nullable = false)
    private String fieldLable;

    @NotNull
    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @NotNull
    @Column(name = "field_type", nullable = false)
    private String fieldType;

    @OneToOne
    @JoinColumn(unique = true)
    private Asset assetSpecificationTypeField;

    @OneToMany(mappedBy = "assetSpecificationTypeField")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AssetSpecificationTypeValue> fields = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetSpecificationType getAssetSpecificationType() {
        return assetSpecificationType;
    }

    public AssetSpecificationTypeField assetSpecificationType(AssetSpecificationType assetSpecificationType) {
        this.assetSpecificationType = assetSpecificationType;
        return this;
    }

    public void setAssetSpecificationType(AssetSpecificationType assetSpecificationType) {
        this.assetSpecificationType = assetSpecificationType;
    }

    public String getFieldLable() {
        return fieldLable;
    }

    public AssetSpecificationTypeField fieldLable(String fieldLable) {
        this.fieldLable = fieldLable;
        return this;
    }

    public void setFieldLable(String fieldLable) {
        this.fieldLable = fieldLable;
    }

    public String getFieldName() {
        return fieldName;
    }

    public AssetSpecificationTypeField fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public AssetSpecificationTypeField fieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Asset getAssetSpecificationTypeField() {
        return assetSpecificationTypeField;
    }

    public AssetSpecificationTypeField assetSpecificationTypeField(Asset asset) {
        this.assetSpecificationTypeField = asset;
        return this;
    }

    public void setAssetSpecificationTypeField(Asset asset) {
        this.assetSpecificationTypeField = asset;
    }

    public Set<AssetSpecificationTypeValue> getFields() {
        return fields;
    }

    public AssetSpecificationTypeField fields(Set<AssetSpecificationTypeValue> assetSpecificationTypeValues) {
        this.fields = assetSpecificationTypeValues;
        return this;
    }

    public AssetSpecificationTypeField addField(AssetSpecificationTypeValue assetSpecificationTypeValue) {
        fields.add(assetSpecificationTypeValue);
        assetSpecificationTypeValue.setAssetSpecificationTypeField(this);
        return this;
    }

    public AssetSpecificationTypeField removeField(AssetSpecificationTypeValue assetSpecificationTypeValue) {
        fields.remove(assetSpecificationTypeValue);
        assetSpecificationTypeValue.setAssetSpecificationTypeField(null);
        return this;
    }

    public void setFields(Set<AssetSpecificationTypeValue> assetSpecificationTypeValues) {
        this.fields = assetSpecificationTypeValues;
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
            ", assetSpecificationType='" + assetSpecificationType + "'" +
            ", fieldLable='" + fieldLable + "'" +
            ", fieldName='" + fieldName + "'" +
            ", fieldType='" + fieldType + "'" +
            '}';
    }
}
