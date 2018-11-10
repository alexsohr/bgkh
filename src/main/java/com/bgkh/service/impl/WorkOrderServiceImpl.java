package com.bgkh.service.impl;

import com.bgkh.service.WorkOrderService;
import com.bgkh.domain.WorkOrder;
import com.bgkh.repository.WorkOrderRepository;
import com.bgkh.service.dto.WorkOrderDTO;
import com.bgkh.service.dto.WorkOrderDTOs;
import com.bgkh.service.mapper.WorkOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing WorkOrder.
 */
@Service
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService {

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
     * Get all the workOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkOrder> findAll(Pageable pageable) {
        log.debug("Request to get all WorkOrders");
        Page<WorkOrder> result = workOrderRepository.findAll(pageable);
        return result;//.map(workOrder -> workOrderMapper.workOrderToWorkOrderDTO(workOrder));
    }

    /**
     * Get all the workOrders.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WorkOrder> findAll() {
        log.debug("Request to get all WorkOrders");
        List<WorkOrder> result = workOrderRepository.findAll();
        return result;
            //.stream().map(workOrderMapper::workOrderToWorkOrderDTO)
            //.collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one workOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public WorkOrder findOne(Long id) {
        log.debug("Request to get WorkOrder : {}", id);
        WorkOrder workOrder = workOrderRepository.findOne(id);
        //WorkOrderDTO workOrderDTO = workOrderMapper.workOrderToWorkOrderDTO(workOrder);
        return workOrder;
    }

    /**
     * Delete the  workOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkOrder : {}", id);
        workOrderRepository.delete(id);
    }

    @Override
    public List<WorkOrder> findAllByAssetId(Long assetId) {
        List<WorkOrder> allByAssetId = workOrderRepository.findAllByAssetId(assetId);
            //.stream()
            //.map(workOrderMapper::workOrderToWorkOrderDTO)
            //.collect(Collectors.toCollection(LinkedList::new));

        return allByAssetId;
    }

    @Override
    public WorkOrderDTOs saveAll(WorkOrderDTOs workOrderDTOs) {
        List<WorkOrder> allByAssetIdForDelete = workOrderRepository.findAllByAssetId(workOrderDTOs.getAssetId());
        List<WorkOrder> workOrders = workOrderDTOs.getWorkOrders().stream().
            map(workOrderMapper::workOrderDTOToWorkOrder).
            collect(Collectors.toList());
        List<WorkOrder> allByAssetId = new ArrayList<>(allByAssetIdForDelete);

        allByAssetIdForDelete.removeAll(workOrders);
        workOrderRepository.delete(allByAssetIdForDelete);

        Iterator<WorkOrder> iterator = workOrders.iterator();
        while (iterator.hasNext()) {
            WorkOrder workOrder = iterator.next();
            if (allByAssetId.contains(workOrder)) {
                iterator.remove();
            } else {
                workOrderRepository.save(workOrder);
            }
        }

        return workOrderDTOs;
    }


    @Override
    public WorkOrderDTOs saveAllWorkOrderTrack(WorkOrderDTOs workOrderDTOs) {

        List<WorkOrder> workOrders = workOrderDTOs.getWorkOrders().stream().
            map(workOrderMapper::workOrderDTOToWorkOrder).
            collect(Collectors.toList());

        Iterator<WorkOrder> iterator = workOrders.iterator();
        while (iterator.hasNext()) {
            WorkOrder workOrder = iterator.next();
            workOrderRepository.save(workOrder);
        }

        return workOrderDTOs;
    }
}
