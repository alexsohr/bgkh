package com.bgkh.repository;

import com.bgkh.domain.AssetSpecificationType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AssetSpecificationType entity.
 */
@SuppressWarnings("unused")
public interface AssetSpecificationTypeRepository extends JpaRepository<AssetSpecificationType,Long> {
    @Override
    @Query("select assetSpecificationType from AssetSpecificationType assetSpecificationType order by name asc")
    List<AssetSpecificationType> findAll();
}
