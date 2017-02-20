package com.bgkh.service.mapper;

import com.bgkh.domain.*;
import com.bgkh.service.dto.AssetDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Asset and its DTO AssetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssetMapper {

    @Mapping(source = "maps.id", target = "mapsId")
    @Mapping(source = "otherFiles.id", target = "otherFilesId")
    AssetDTO assetToAssetDTO(Asset asset);

    List<AssetDTO> assetsToAssetDTOs(List<Asset> assets);

    @Mapping(target = "workOrders", ignore = true)
    @Mapping(source = "mapsId", target = "maps")
    @Mapping(source = "otherFilesId", target = "otherFiles")
    Asset assetDTOToAsset(AssetDTO assetDTO);

    List<Asset> assetDTOsToAssets(List<AssetDTO> assetDTOs);

    default UploadFile uploadFileFromId(Long id) {
        if (id == null) {
            return null;
        }
        UploadFile uploadFile = new UploadFile();
        uploadFile.setId(id);
        return uploadFile;
    }
}
