package com.bgkh.service.mapper;

import com.bgkh.domain.*;
import com.bgkh.service.dto.WorkOrderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity WorkOrder and its DTO WorkOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkOrderTemplateMapper.class})
public interface WorkOrderMapper {

    @Mapping(source = "asset.id", target = "assetId")
    @Mapping(source = "workOrderTemplate.id", target = "workOrderTemplateId")
    WorkOrderDTO workOrderToWorkOrderDTO(WorkOrder workOrder);

    List<WorkOrderDTO> workOrdersToWorkOrderDTOs(List<WorkOrder> workOrders);

    @Mapping(source = "assetId", target = "asset")
    @Mapping(source = "workOrderTemplateId", target = "workOrderTemplate")
    WorkOrder workOrderDTOToWorkOrder(WorkOrderDTO workOrderDTO);

    List<WorkOrder> workOrderDTOsToWorkOrders(List<WorkOrderDTO> workOrderDTOs);

    default Asset assetFromId(Long id) {
        if (id == null) {
            return null;
        }
        Asset asset = new Asset();
        asset.setId(id);
        return asset;
    }

    default WorkOrderTemplate workOrderTemplateFromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkOrderTemplate workOrderTemplate = new WorkOrderTemplate();
        workOrderTemplate.setId(id);
        return workOrderTemplate;
    }
}
