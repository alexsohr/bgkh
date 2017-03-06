package com.bgkh.service;

import com.bgkh.service.dto.AssetSpecificationTypeValueDTO;
import java.util.List;

/**
 * Service Interface for managing AssetSpecificationTypeValue.
 */
public interface AssetSpecificationTypeValueService {

    /**
     * Save a assetSpecificationTypeValue.
     *
     * @param assetSpecificationTypeValueDTO the entity to save
     * @return the persisted entity
     */
    AssetSpecificationTypeValueDTO save(AssetSpecificationTypeValueDTO assetSpecificationTypeValueDTO);

    /**
     *  Get all the assetSpecificationTypeValues.
     *
     *  @return the list of entities
     */
    List<AssetSpecificationTypeValueDTO> findAll();

    /**
     *  Get the "id" assetSpecificationTypeValue.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    List<AssetSpecificationTypeValueDTO> findAllByAssetId(Long id);

    /**
     *  Delete the "id" assetSpecificationTypeValue.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
