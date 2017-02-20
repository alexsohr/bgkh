package com.bgkh.repository;

import com.bgkh.domain.AssetSpecificationTypeField;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AssetSpecificationTypeField entity.
 */
@SuppressWarnings("unused")
public interface AssetSpecificationTypeFieldRepository extends JpaRepository<AssetSpecificationTypeField,Long> {

}
