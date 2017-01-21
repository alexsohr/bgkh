package com.bgkh.service.impl;

import com.bgkh.service.WorkOrderService;
import com.bgkh.domain.WorkOrder;
import com.bgkh.repository.WorkOrderRepository;
import com.bgkh.service.dto.WorkOrderDTO;
import com.bgkh.service.mapper.WorkOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing WorkOrder.
 */
@Service
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService{

    private final Logger log = LoggerFactory.getLogger(WorkOrderServiceImpl.class);
    
    @Inject
    private WorkOrderRepository workOrderRepository;

    @Inject
    private WorkOrderMapper workOrderMapper;

    /**
     * Save a workOrder.
     *
     * @param workOrderDTO the entity to save
     * @return the persisted entity
     */
    public WorkOrderDTO save(WorkOrderDTO workOrderDTO) {
        log.debug("Request to save WorkOrder : {}", workOrderDTO);
        WorkOrder workOrder = workOrderMapper.workOrderDTOToWorkOrder(workOrderDTO);
        workOrder = workOrderRepository.save(workOrder);
        WorkOrderDTO result = workOrderMapper.workOrderToWorkOrderDTO(workOrder);
        return result;
    }

    /**
     *  Get all the workOrders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<WorkOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkOrders");
        Page<WorkOrder> result = workOrderRepository.findAll(pageable);
        return result.map(workOrder -> workOrderMapper.workOrderToWorkOrderDTO(workOrder));
    }

    /**
     *  Get one workOrder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WorkOrderDTO findOne(Long id) {
        log.debug("Request to get WorkOrder : {}", id);
        WorkOrder workOrder = workOrderRepository.findOne(id);
        WorkOrderDTO workOrderDTO = workOrderMapper.workOrderToWorkOrderDTO(workOrder);
        return workOrderDTO;
    }

    /**
     *  Delete the  workOrder by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkOrder : {}", id);
        workOrderRepository.delete(id);
    }
}
