package com.bgkh.service.mapper;

import com.bgkh.domain.*;
import com.bgkh.service.dto.WorkOrderTemplateDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity WorkOrderTemplate and its DTO WorkOrderTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkOrderTemplateMapper {

    @Mapping(source = "assetSpecificationType.id", target = "assetSpecificationTypeId")
    WorkOrderTemplateDTO workOrderTemplateToWorkOrderTemplateDTO(WorkOrderTemplate workOrderTemplate);


    @Mapping(source = "assetSpecificationTypeId", target = "assetSpecificationType")
    WorkOrderTemplate workOrderTemplateDTOToWorkOrderTemplate(WorkOrderTemplateDTO workOrderTemplateDTO);

    List<WorkOrderTemplate> workOrderTemplateDTOsToWorkOrderTemplates(List<WorkOrderTemplateDTO> workOrderTemplateDTOs);

    default AssetSpecificationType assetSpecificationTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        AssetSpecificationType assetSpecificationType = new AssetSpecificationType();
        assetSpecificationType.setId(id);
        return assetSpecificationType;
    }
}
