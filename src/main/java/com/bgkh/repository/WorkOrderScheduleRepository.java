package com.bgkh.repository;

import com.bgkh.domain.WorkOrderSchedule;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkOrderSchedule entity.
 */
@SuppressWarnings("unused")
public interface WorkOrderScheduleRepository extends JpaRepository<WorkOrderSchedule,Long> {

    @Query("select schedule from WorkOrderSchedule schedule where schedule.workOrder.id = :workOrderId and schedule.asset.id = :assetId and schedule.workOrderTemplate.id = :templateId")
    List<WorkOrderSchedule> findAllByWorkOrder(@Param("workOrderId") Long workOrderId, @Param("assetId") Long assetId, @Param("templateId") Long templateId);
}
