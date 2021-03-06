package com.bgkh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.bgkh.domain.enumeration.AssetType;

/**
 * A Asset.
 */
@Entity
@Table(name = "asset")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(min = 1, max = 250)
    @Column(name = "location", length = 250)
    private String location;

    @Size(min = 1, max = 500)
    @Column(name = "details", length = 500)
    private String details;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type")
    private AssetType assetType;

    @Size(min = 1, max = 250)
    @Column(name = "manufacture", length = 250)
    private String manufacture;

    @DecimalMin(value = "0")
    @Column(name = "capacity", precision=10, scale=2)
    private BigDecimal capacity;

    @Min(value = 1800)
    @Column(name = "year")
    private Integer year;

    @Column(name = "strategic")
    private boolean strategic;

    @Column(name = "capacity_unit")
    private String unit;

    @ManyToOne
    private User supervisor;

    @ManyToOne
    private User technician;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "asset_maps",
               joinColumns = @JoinColumn(name="assets_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="maps_id", referencedColumnName="ID"))
    private Set<UploadFile> maps = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "asset_other_files",
               joinColumns = @JoinColumn(name="assets_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="other_files_id", referencedColumnName="ID"))
    private Set<UploadFile> otherFiles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="asset")
    private Set<AssetSpecificationTypeValue> assetSpecificationTypeValues;

    @ManyToOne
    private AssetSpecificationType assetSpecificationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public Asset parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public Asset name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public Asset location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public Asset details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public Asset code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public Asset assetType(AssetType assetType) {
        this.assetType = assetType;
        return this;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public String getManufacture() {
        return manufacture;
    }

    public Asset manufacture(String manufacture) {
        this.manufacture = manufacture;
        return this;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public Asset capacity(BigDecimal capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public Integer getYear() {
        return year;
    }

    public Asset year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getUnit() {
        return unit;
    }

    public Asset unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public Asset supervisor(User user) {
        this.supervisor = user;
        return this;
    }

    public void setSupervisor(User user) {
        this.supervisor = user;
    }

    public User getTechnician() {
        return technician;
    }

    public Asset technician(User user) {
        this.technician = user;
        return this;
    }

    public void setTechnician(User user) {
        this.technician = user;
    }

    public Set<UploadFile> getMaps() {
        return maps;
    }

    public Asset maps(Set<UploadFile> uploadFiles) {
        this.maps = uploadFiles;
        return this;
    }

    public Asset addMaps(UploadFile uploadFile) {
        maps.add(uploadFile);
        uploadFile.getAssetMaps().add(this);
        return this;
    }

    public Asset removeMaps(UploadFile uploadFile) {
        maps.remove(uploadFile);
        uploadFile.getAssetMaps().remove(this);
        return this;
    }

    public void setMaps(Set<UploadFile> uploadFiles) {
        this.maps = uploadFiles;
    }

    public Set<UploadFile> getOtherFiles() {
        return otherFiles;
    }

    public Asset otherFiles(Set<UploadFile> uploadFiles) {
        this.otherFiles = uploadFiles;
        return this;
    }

    public Asset addOtherFiles(UploadFile uploadFile) {
        otherFiles.add(uploadFile);
        uploadFile.getAssetOtherFiles().add(this);
        return this;
    }

    public Asset removeOtherFiles(UploadFile uploadFile) {
        otherFiles.remove(uploadFile);
        uploadFile.getAssetOtherFiles().remove(this);
        return this;
    }

    public void setOtherFiles(Set<UploadFile> uploadFiles) {
        this.otherFiles = uploadFiles;
    }

    public AssetSpecificationType getAssetSpecificationType() {
        return assetSpecificationType;
    }

    public Asset assetSpecificationType(AssetSpecificationType assetSpecificationType) {
        this.assetSpecificationType = assetSpecificationType;
        return this;
    }

    public void setAssetSpecificationType(AssetSpecificationType assetSpecificationType) {
        this.assetSpecificationType = assetSpecificationType;
    }

    public boolean isStrategic() {
        return strategic;
    }

    public void setStrategic(boolean strategic) {
        this.strategic = strategic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Asset asset = (Asset) o;
        if (asset.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, asset.id);
    }

    public Set<AssetSpecificationTypeValue> getAssetSpecificationTypeValues() {
        return assetSpecificationTypeValues;
    }

    public void setAssetSpecificationTypeValues(Set<AssetSpecificationTypeValue> assetSpecificationTypeValues) {
        this.assetSpecificationTypeValues = assetSpecificationTypeValues;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Asset{" +
            "id=" + id +
            ", parentId='" + parentId + "'" +
            ", name='" + name + "'" +
            ", location='" + location + "'" +
            ", details='" + details + "'" +
            ", code='" + code + "'" +
            ", assetType='" + assetType + "'" +
            ", manufacture='" + manufacture + "'" +
            ", capacity='" + capacity + "'" +
            ", year='" + year + "'" +
            ", unit='" + unit + "'" +
            '}';
    }
}
