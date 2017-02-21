package com.bgkh.repository;

import com.bgkh.domain.UploadFile;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UploadFile entity.
 */
@SuppressWarnings("unused")
public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {

}
