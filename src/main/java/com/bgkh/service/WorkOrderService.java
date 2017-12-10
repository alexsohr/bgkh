package com.bgkh.service;

import com.bgkh.domain.WorkOrder;
import com.bgkh.service.dto.WorkOrderDTO;
import com.bgkh.service.dto.WorkOrderDTOs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Service Interface for managing WorkOrder.
 */
public interface WorkOrderService {

    /**
     * Save a workOrder.
     *
     * @param workOrderDTO the entity to save
     * @return the persisted entity
     */
    WorkOrderDTO save(WorkOrderDTO workOrderDTO);

    /**
     *  Get all the workOrders.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WorkOrderDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" workOrder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WorkOrderDTO findOne(Long id);

    /**
     *  Delete the "id" workOrder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<WorkOrderDTO> findAllByAssetId(Long assetId);

    WorkOrderDTOs saveAll(WorkOrderDTOs workOrderDTOs);

    WorkOrderDTOs saveAllWorkOrderTrack(WorkOrderDTOs workOrderDTOs);
}
