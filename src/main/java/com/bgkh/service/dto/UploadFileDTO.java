package com.bgkh.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the UploadFile entity.
 */
public class UploadFileDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 250)
    private String location;

    @NotNull
    private Boolean deleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UploadFileDTO uploadFileDTO = (UploadFileDTO) o;

        if ( ! Objects.equals(id, uploadFileDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UploadFileDTO{" +
            "id=" + id +
            ", location='" + location + "'" +
            ", deleted='" + deleted + "'" +
            '}';
    }
}
