package com.bgkh.service.impl;

import com.bgkh.service.WorkOrderScheduleService;
import com.bgkh.domain.WorkOrderSchedule;
import com.bgkh.repository.WorkOrderScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing WorkOrderSchedule.
 */
@Service
@Transactional
public class WorkOrderScheduleServiceImpl implements WorkOrderScheduleService{

    private final Logger log = LoggerFactory.getLogger(WorkOrderScheduleServiceImpl.class);
    
    @Inject
    private WorkOrderScheduleRepository workOrderScheduleRepository;

    /**
     * Save a workOrderSchedule.
     *
     * @param workOrderSchedule the entity to save
     * @return the persisted entity
     */
    public WorkOrderSchedule save(WorkOrderSchedule workOrderSchedule) {
        log.debug("Request to save WorkOrderSchedule : {}", workOrderSchedule);
        WorkOrderSchedule result = workOrderScheduleRepository.save(workOrderSchedule);
        return result;
    }

    /**
     *  Get all the workOrderSchedules.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<WorkOrderSchedule> findAll() {
        log.debug("Request to get all WorkOrderSchedules");
        List<WorkOrderSchedule> result = workOrderScheduleRepository.findAll();

        return result;
    }

    /**
     *  Get one workOrderSchedule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WorkOrderSchedule findOne(Long id) {
        log.debug("Request to get WorkOrderSchedule : {}", id);
        WorkOrderSchedule workOrderSchedule = workOrderScheduleRepository.findOne(id);
        return workOrderSchedule;
    }

    /**
     *  Delete the  workOrderSchedule by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkOrderSchedule : {}", id);
        workOrderScheduleRepository.delete(id);
    }
}
