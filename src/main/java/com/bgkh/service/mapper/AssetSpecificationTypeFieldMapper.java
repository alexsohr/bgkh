package com.bgkh.service.mapper;

import com.bgkh.domain.*;
import com.bgkh.service.dto.AssetSpecificationTypeFieldDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity AssetSpecificationTypeField and its DTO AssetSpecificationTypeFieldDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssetSpecificationTypeFieldMapper {

    @Mapping(source = "assetSpecificationType.id", target = "assetSpecificationTypeId")
    AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO(AssetSpecificationTypeField assetSpecificationTypeField);

    List<AssetSpecificationTypeFieldDTO> assetSpecificationTypeFieldsToAssetSpecificationTypeFieldDTOs(List<AssetSpecificationTypeField> assetSpecificationTypeFields);

    @Mapping(source = "assetSpecificationTypeId", target = "assetSpecificationType")
    AssetSpecificationTypeField assetSpecificationTypeFieldDTOToAssetSpecificationTypeField(AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO);

    List<AssetSpecificationTypeField> assetSpecificationTypeFieldDTOsToAssetSpecificationTypeFields(List<AssetSpecificationTypeFieldDTO> assetSpecificationTypeFieldDTOs);

    default AssetSpecificationType assetSpecificationTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        AssetSpecificationType assetSpecificationType = new AssetSpecificationType();
        assetSpecificationType.setId(id);
        return assetSpecificationType;
    }
}
