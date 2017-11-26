package com.bgkh.repository;

import com.bgkh.domain.WorkOrder;

import com.bgkh.service.dto.WorkOrderDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkOrder entity.
 */
@SuppressWarnings("unused")
public interface WorkOrderRepository extends JpaRepository<WorkOrder,Long> {

    @Query("select workOrder from WorkOrder workOrder where workOrder.asset.id = :assetId")
    List<WorkOrder> findAllByAssetId(@Param("assetId") Long assetId);
}
