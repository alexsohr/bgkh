package com.bgkh.service.mapper;

import com.bgkh.domain.*;
import com.bgkh.service.dto.AssetDTO;

import com.bgkh.service.dto.AssetDTOs;
import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Asset and its DTO AssetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssetMapper {

    AssetDTO assetToAssetDTO(Asset asset);

    @Mapping(target = "workOrders", ignore = true)
    Asset assetDTOToAsset(AssetDTO assetDTO);

}
