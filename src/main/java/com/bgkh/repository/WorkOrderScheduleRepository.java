package com.bgkh.repository;

import com.bgkh.domain.WorkOrderSchedule;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkOrderSchedule entity.
 */
@SuppressWarnings("unused")
public interface WorkOrderScheduleRepository extends JpaRepository<WorkOrderSchedule,Long> {

}
