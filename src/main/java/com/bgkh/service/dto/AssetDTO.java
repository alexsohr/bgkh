package com.bgkh.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.bgkh.domain.enumeration.AssetType;

/**
 * A DTO for the Asset entity.
 */
public class AssetDTO implements Serializable {

    private Long id;

    @NotNull
    private Long parentId;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @Size(min = 3, max = 250)
    private String location;

    @Size(min = 3, max = 500)
    private String details;

    @NotNull
    @Size(min = 1, max = 50)
    private String code;

    private AssetType assetType;

    @Size(min = 3, max = 250)
    private String manufacture;

    @Size(min = 3, max = 250)
    private String typeVal;

    @Min(value = 0)
    private Integer capacity;

    @Min(value = 1800)
    private Integer year;


    private Long supervisorId;

    private Long technicianId;

    private String supervisorFirstName;

    private String technicianFirstName;

    private String supervisorLastName;

    private String technicianLastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }
    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }
    public String getTypeVal() {
        return typeVal;
    }

    public void setTypeVal(String typeVal) {
        this.typeVal = typeVal;
    }
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long userId) {
        this.supervisorId = userId;
    }

    public Long getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(Long userId) {
        this.technicianId = userId;
    }

    public String getSupervisorLastName() {
        return supervisorLastName;
    }

    public void setSupervisorLastName(String supervisorLastName) {
        this.supervisorLastName = supervisorLastName;
    }

    public String getTechnicianLastName() {
        return technicianLastName;
    }

    public void setTechnicianLastName(String technicianLastName) {
        this.technicianLastName = technicianLastName;
    }

    public String getSupervisorFirstName() {
        return supervisorFirstName;
    }

    public void setSupervisorFirstName(String supervisorFirstName) {
        this.supervisorFirstName = supervisorFirstName;
    }

    public String getTechnicianFirstName() {
        return technicianFirstName;
    }

    public void setTechnicianFirstName(String technicianFirstName) {
        this.technicianFirstName = technicianFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetDTO assetDTO = (AssetDTO) o;

        if ( ! Objects.equals(id, assetDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetDTO{" +
            "id=" + id +
            ", parentId='" + parentId + "'" +
            ", name='" + name + "'" +
            ", location='" + location + "'" +
            ", details='" + details + "'" +
            ", code='" + code + "'" +
            ", assetType='" + assetType + "'" +
            ", manufacture='" + manufacture + "'" +
            ", typeVal='" + typeVal + "'" +
            ", capacity='" + capacity + "'" +
            ", year='" + year + "'" +
            ", supervisorId='" + supervisorId + "'" +
            ", technicianId='" + technicianId + "'" +
            ", supervisorFirstName='" + supervisorFirstName + "'" +
            ", technicianFirstName='" + technicianFirstName + "'" +
            ", supervisorLastName='" + supervisorLastName + "'" +
            ", technicianLastName='" + technicianLastName + "'" +
            '}';
    }
}
