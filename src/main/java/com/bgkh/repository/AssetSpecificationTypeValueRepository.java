package com.bgkh.repository;

import com.bgkh.domain.AssetSpecificationTypeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the AssetSpecificationTypeValue entity.
 */
@SuppressWarnings("unused")
public interface AssetSpecificationTypeValueRepository extends JpaRepository<AssetSpecificationTypeValue,Long> {

    @Query("select typeValue from AssetSpecificationTypeValue typeValue where typeValue.asset.id = :assetId order by typeValue.assetSpecificationTypeField.id asc")
    List<AssetSpecificationTypeValue> findAllByAssetId(@Param("assetId") Long assetId);
}
