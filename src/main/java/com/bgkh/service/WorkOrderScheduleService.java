package com.bgkh.service;

import com.bgkh.domain.WorkOrderSchedule;
import java.util.List;

/**
 * Service Interface for managing WorkOrderSchedule.
 */
public interface WorkOrderScheduleService {

    /**
     * Save a workOrderSchedule.
     *
     * @param workOrderSchedule the entity to save
     * @return the persisted entity
     */
    WorkOrderSchedule save(WorkOrderSchedule workOrderSchedule);

    /**
     *  Get all the workOrderSchedules.
     *
     *  @return the list of entities
     */
    List<WorkOrderSchedule> findAll();
    List<WorkOrderSchedule> findAllByUser();
    /**
     *  Get the "id" workOrderSchedule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WorkOrderSchedule findOne(Long id);

    /**
     *  Delete the "id" workOrderSchedule.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
