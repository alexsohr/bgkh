package com.bgkh.service;

import com.bgkh.service.dto.AssetSpecificationTypeFieldDTO;
import java.util.List;

/**
 * Service Interface for managing AssetSpecificationTypeField.
 */
public interface AssetSpecificationTypeFieldService {

    /**
     * Save a assetSpecificationTypeField.
     *
     * @param assetSpecificationTypeFieldDTO the entity to save
     * @return the persisted entity
     */
    AssetSpecificationTypeFieldDTO save(AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO);

    /**
     *  Get all the assetSpecificationTypeFields.
     *  
     *  @return the list of entities
     */
    List<AssetSpecificationTypeFieldDTO> findAll();

    /**
     *  Get the "id" assetSpecificationTypeField.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AssetSpecificationTypeFieldDTO findOne(Long id);

    /**
     *  Delete the "id" assetSpecificationTypeField.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
