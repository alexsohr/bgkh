package com.bgkh.service.mapper;

import com.bgkh.domain.*;
import com.bgkh.service.dto.AssetSpecificationTypeValueDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity AssetSpecificationTypeValue and its DTO AssetSpecificationTypeValueDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssetSpecificationTypeValueMapper {

    @Mapping(source = "assetSpecificationTypeField.id", target = "assetSpecificationTypeFieldId")
    @Mapping(source = "asset.id", target = "assetId")
    AssetSpecificationTypeValueDTO assetSpecificationTypeValueToAssetSpecificationTypeValueDTO(AssetSpecificationTypeValue assetSpecificationTypeValue);

    List<AssetSpecificationTypeValueDTO> assetSpecificationTypeDataToAssetSpecificationTypeValueDTOs(List<AssetSpecificationTypeValue> assetSpecificationTypeData);

    @Mapping(source = "assetSpecificationTypeFieldId", target = "assetSpecificationTypeField")
    @Mapping(source = "assetId", target = "asset")
    AssetSpecificationTypeValue assetSpecificationTypeValueDTOToAssetSpecificationTypeValue(AssetSpecificationTypeValueDTO assetSpecificationTypeValueDTO);

    List<AssetSpecificationTypeValue> assetSpecificationTypeValueDTOsToAssetSpecificationTypeValues(List<AssetSpecificationTypeValueDTO> assetSpecificationTypeValueDTOs);

    default AssetSpecificationTypeField assetSpecificationTypeFieldFromId(Long id) {
        if (id == null) {
            return null;
        }
        AssetSpecificationTypeField assetSpecificationTypeField = new AssetSpecificationTypeField();
        assetSpecificationTypeField.setId(id);
        return assetSpecificationTypeField;
    }

    default Asset assetFromId(Long id) {
        if (id == null) {
            return null;
        }
        Asset asset = new Asset();
        asset.setId(id);
        return asset;
    }
}
