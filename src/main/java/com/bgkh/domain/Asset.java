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
    @Size(min = 3, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(min = 3, max = 250)
    @Column(name = "location", length = 250)
    private String location;

    @Size(min = 3, max = 500)
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

    @Size(min = 1, max = 250)
    @Column(name = "type_val", length = 250)
    private String typeVal;

    @Min(value = 0)
    @Column(name = "capacity")
    private Integer capacity;

    @Min(value = 1800)
    @Column(name = "year")
    private Integer year;

    @OneToMany(mappedBy = "asset")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkOrder> workOrders = new HashSet<>();

    @ManyToOne
    private UploadFile maps;

    @ManyToOne
    private UploadFile otherFiles;

    @ManyToOne
    private User supervisor;

    @ManyToOne
    private User technician;

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

    public String getTypeVal() {
        return typeVal;
    }

    public Asset typeVal(String typeVal) {
        this.typeVal = typeVal;
        return this;
    }

    public void setTypeVal(String typeVal) {
        this.typeVal = typeVal;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Asset capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
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

    public Set<WorkOrder> getWorkOrders() {
        return workOrders;
    }

    public Asset workOrders(Set<WorkOrder> workOrders) {
        this.workOrders = workOrders;
        return this;
    }

    public Asset addWorkOrder(WorkOrder workOrder) {
        workOrders.add(workOrder);
        workOrder.setAsset(this);
        return this;
    }

    public Asset removeWorkOrder(WorkOrder workOrder) {
        workOrders.remove(workOrder);
        workOrder.setAsset(null);
        return this;
    }

    public void setWorkOrders(Set<WorkOrder> workOrders) {
        this.workOrders = workOrders;
    }

    public UploadFile getMaps() {
        return maps;
    }

    public Asset maps(UploadFile uploadFile) {
        this.maps = uploadFile;
        return this;
    }

    public void setMaps(UploadFile uploadFile) {
        this.maps = uploadFile;
    }

    public UploadFile getOtherFiles() {
        return otherFiles;
    }

    public Asset otherFiles(UploadFile uploadFile) {
        this.otherFiles = uploadFile;
        return this;
    }

    public void setOtherFiles(UploadFile uploadFile) {
        this.otherFiles = uploadFile;
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
            ", typeVal='" + typeVal + "'" +
            ", capacity='" + capacity + "'" +
            ", year='" + year + "'" +
            '}';
    }
}
