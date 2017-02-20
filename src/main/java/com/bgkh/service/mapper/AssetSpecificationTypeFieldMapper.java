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

    @Mapping(source = "assetSpecificationTypeField.id", target = "assetSpecificationTypeFieldId")
    AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO(AssetSpecificationTypeField assetSpecificationTypeField);

    List<AssetSpecificationTypeFieldDTO> assetSpecificationTypeFieldsToAssetSpecificationTypeFieldDTOs(List<AssetSpecificationTypeField> assetSpecificationTypeFields);

    @Mapping(source = "assetSpecificationTypeFieldId", target = "assetSpecificationTypeField")
    @Mapping(target = "fields", ignore = true)
    AssetSpecificationTypeField assetSpecificationTypeFieldDTOToAssetSpecificationTypeField(AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO);

    List<AssetSpecificationTypeField> assetSpecificationTypeFieldDTOsToAssetSpecificationTypeFields(List<AssetSpecificationTypeFieldDTO> assetSpecificationTypeFieldDTOs);

    default Asset assetFromId(Long id) {
        if (id == null) {
            return null;
        }
        Asset asset = new Asset();
        asset.setId(id);
        return asset;
    }
}
