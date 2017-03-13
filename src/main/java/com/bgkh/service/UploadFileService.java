package com.bgkh.service;

import com.bgkh.service.dto.UploadFileDTO;
import java.util.List;

/**
 * Service Interface for managing UploadFile.
 */
public interface UploadFileService {

    /**
     * Save a uploadFile.
     *
     * @param uploadFileDTO the entity to save
     * @return the persisted entity
     */
    UploadFileDTO save(UploadFileDTO uploadFileDTO);

    /**
     *  Get all the uploadFiles.
     *
     *  @return the list of entities
     */
    List<UploadFileDTO> findAll();

    /**
     *  Get the "id" uploadFile.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UploadFileDTO findOne(Long id);

    /**
     *  Delete the "id" uploadFile.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    String getUploadPath();
}
