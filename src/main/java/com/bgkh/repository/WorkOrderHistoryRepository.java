package com.bgkh.repository;

import com.bgkh.domain.WorkOrderHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkOrderHistory entity.
 */
@SuppressWarnings("unused")
public interface WorkOrderHistoryRepository extends JpaRepository<WorkOrderHistory,Long> {

    @Query("select workOrderHistory from WorkOrderHistory workOrderHistory where workOrderHistory.workOrderSchedule.asset.id = :id order by workOrderHistory.createDate desc")
    List<WorkOrderHistory> findAllByAssetId(@Param("id") Long id);
}
