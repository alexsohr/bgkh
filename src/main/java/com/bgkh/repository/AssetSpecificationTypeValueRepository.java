package com.bgkh.repository;

import com.bgkh.domain.AssetSpecificationTypeValue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AssetSpecificationTypeValue entity.
 */
@SuppressWarnings("unused")
public interface AssetSpecificationTypeValueRepository extends JpaRepository<AssetSpecificationTypeValue,Long> {

}
