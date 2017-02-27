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

/**
 * A UploadFile.
 */
@Entity
@Table(name = "upload_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UploadFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 250)
    @Column(name = "location", length = 250, nullable = false)
    private String location;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @ManyToMany(mappedBy = "maps")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Asset> assetMaps = new HashSet<>();

    @ManyToMany(mappedBy = "otherFiles")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Asset> assetOtherFiles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public UploadFile location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public UploadFile deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Asset> getAssetMaps() {
        return assetMaps;
    }

    public UploadFile assetMaps(Set<Asset> assets) {
        this.assetMaps = assets;
        return this;
    }

    public UploadFile addAssetMaps(Asset asset) {
        assetMaps.add(asset);
        asset.getMaps().add(this);
        return this;
    }

    public UploadFile removeAssetMaps(Asset asset) {
        assetMaps.remove(asset);
        asset.getMaps().remove(this);
        return this;
    }

    public void setAssetMaps(Set<Asset> assets) {
        this.assetMaps = assets;
    }

    public Set<Asset> getAssetOtherFiles() {
        return assetOtherFiles;
    }

    public UploadFile assetOtherFiles(Set<Asset> assets) {
        this.assetOtherFiles = assets;
        return this;
    }

    public UploadFile addAssetOtherFiles(Asset asset) {
        assetOtherFiles.add(asset);
        asset.getOtherFiles().add(this);
        return this;
    }

    public UploadFile removeAssetOtherFiles(Asset asset) {
        assetOtherFiles.remove(asset);
        asset.getOtherFiles().remove(this);
        return this;
    }

    public void setAssetOtherFiles(Set<Asset> assets) {
        this.assetOtherFiles = assets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UploadFile uploadFile = (UploadFile) o;
        if (uploadFile.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, uploadFile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UploadFile{" +
            "id=" + id +
            ", location='" + location + "'" +
            ", deleted='" + deleted + "'" +
            '}';
    }
}
