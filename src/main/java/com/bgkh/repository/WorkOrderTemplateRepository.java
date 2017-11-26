package com.bgkh.repository;

import com.bgkh.domain.WorkOrderTemplate;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkOrderTemplate entity.
 */
@SuppressWarnings("unused")
public interface WorkOrderTemplateRepository extends JpaRepository<WorkOrderTemplate,Long> {

    @Query("select workOrderTemplate from WorkOrderTemplate workOrderTemplate where workOrderTemplate.assetSpecificationType.id = :assetTypeId")
    List<WorkOrderTemplate> findAllByAssetTypeId(@Param("assetTypeId") Long assetTypeId);
}
