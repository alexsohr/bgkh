package com.bgkh.service.mapper;

import com.bgkh.domain.*;
import com.bgkh.service.dto.UploadFileDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UploadFile and its DTO UploadFileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UploadFileMapper {

    UploadFileDTO uploadFileToUploadFileDTO(UploadFile uploadFile);

    List<UploadFileDTO> uploadFilesToUploadFileDTOs(List<UploadFile> uploadFiles);

    @Mapping(target = "assetMaps", ignore = true)
    @Mapping(target = "assetOtherFiles", ignore = true)
    UploadFile uploadFileDTOToUploadFile(UploadFileDTO uploadFileDTO);

    List<UploadFile> uploadFileDTOsToUploadFiles(List<UploadFileDTO> uploadFileDTOs);
}
