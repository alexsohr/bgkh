package com.bgkh.repository;

import com.bgkh.domain.WorkOrderHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkOrderHistory entity.
 */
@SuppressWarnings("unused")
public interface WorkOrderHistoryRepository extends JpaRepository<WorkOrderHistory,Long> {

}
