package com.bgkh.service.mapper;

import com.bgkh.domain.*;
import com.bgkh.service.dto.WorkOrderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity WorkOrder and its DTO WorkOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkOrderMapper {

    @Mapping(source = "asset.id", target = "assetId")
    WorkOrderDTO workOrderToWorkOrderDTO(WorkOrder workOrder);

    List<WorkOrderDTO> workOrdersToWorkOrderDTOs(List<WorkOrder> workOrders);

    @Mapping(source = "assetId", target = "asset")
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
}
