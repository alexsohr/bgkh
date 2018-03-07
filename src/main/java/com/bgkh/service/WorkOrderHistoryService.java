package com.bgkh.service;

import com.bgkh.domain.WorkOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the WorkOrderHistory entity.
 */
@SuppressWarnings("unused")
public interface WorkOrderHistoryService {
    WorkOrderHistory save(WorkOrderHistory workOrderHistory);
}
