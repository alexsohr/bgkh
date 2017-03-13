package com.bgkh.service;

import com.bgkh.service.dto.AssetDTO;
import com.bgkh.service.dto.AssetDTOs;

import java.util.List;

/**
 * Service Interface for managing Asset.
 */
public interface AssetService {

    /**
     * Save a asset.
     *
     * @param assetDTO the entity to save
     * @return the persisted entity
     */
    AssetDTO save(AssetDTO assetDTO);

    /**
     * Copy a asset.
     *
     * @param assetDTOs the entities to copy
     * @return the persisted entity
     */
    AssetDTOs copy(AssetDTOs assetDTOs);

    /**
     * Save all assets.
     *
     * @param assetDTOs the entities to save
     * @return the persisted entity
     */
    AssetDTOs saveAll(AssetDTOs assetDTOs);

    /**
     *  Get all the assets.
     *
     *  @return the list of entities
     */
    List<AssetDTO> findAll();


    List<String> findAllManufactures();

    List<String> findAllAssetNames();

    List<String> findAllAssetLocations();

    /**
     *  Get the "id" asset.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AssetDTO findOne(Long id);

    /**
     *  Delete the "id" asset.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
