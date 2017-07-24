package com.bgkh.service.mapper;

import com.bgkh.domain.*;
import com.bgkh.service.dto.AssetDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Asset and its DTO AssetDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserMapper.class, UploadFileMapper.class, UploadFileMapper.class})
public interface AssetMapper {

    @Mapping(source = "supervisor.id", target = "supervisorId")
    @Mapping(source = "technician.id", target = "technicianId")
    @Mapping(target = "strategic", expression = "java(String.valueOf(asset.isStrategic()))")
    @Mapping(source = "technician.firstName", target = "technicianFirstName")
    @Mapping(source = "technician.lastName", target = "technicianLastName")
    @Mapping(source = "supervisor.firstName", target = "supervisorFirstName")
    @Mapping(source = "supervisor.lastName", target = "supervisorLastName")
    @Mapping(source = "assetSpecificationType.id", target = "assetSpecificationTypeId")
    AssetDTO assetToAssetDTO(Asset asset);

    List<AssetDTO> assetsToAssetDTOs(List<Asset> assets);

    @Mapping(target = "workOrders", ignore = true)
    @Mapping(target = "strategic" , expression = "java(Boolean.valueOf(assetDTO.getStrategic()))")
    @Mapping(source = "supervisorId", target = "supervisor")
    @Mapping(source = "technicianId", target = "technician")
    @Mapping(source = "assetSpecificationTypeId", target = "assetSpecificationType")
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

    default AssetSpecificationType assetSpecificationTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        AssetSpecificationType assetSpecificationType = new AssetSpecificationType();
        assetSpecificationType.setId(id);
        return assetSpecificationType;
    }
}
