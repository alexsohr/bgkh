package com.bgkh.repository;

import com.bgkh.domain.WorkOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkOrder entity.
 */
@SuppressWarnings("unused")
public interface WorkOrderRepository extends JpaRepository<WorkOrder,Long> {

}
