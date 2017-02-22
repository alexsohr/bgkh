package com.bgkh.service.impl;

import com.bgkh.service.AssetService;
import com.bgkh.domain.Asset;
import com.bgkh.repository.AssetRepository;
import com.bgkh.service.dto.AssetDTO;
import com.bgkh.service.dto.AssetDTOs;
import com.bgkh.service.mapper.AssetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Asset.
 */
@Service
@Transactional
public class AssetServiceImpl implements AssetService{

    private final Logger log = LoggerFactory.getLogger(AssetServiceImpl.class);

    @Inject
    private AssetRepository assetRepository;

    @Inject
    private AssetMapper assetMapper;

    /**
     * Save a asset.
     *
     * @param assetDTO the entity to save
     * @return the persisted entity
     */
    public AssetDTO save(AssetDTO assetDTO) {
        log.debug("Request to save Asset : {}", assetDTO);
        Asset asset = assetMapper.assetDTOToAsset(assetDTO);
        asset = assetRepository.save(asset);
        AssetDTO result = assetMapper.assetToAssetDTO(asset);
        return result;
    }

    @Override
    @Transactional
    public AssetDTOs copy(AssetDTOs assetDTOs) {
        log.debug("Request to copy Assets : {}", assetDTOs);
        ArrayList<AssetDTO> response = new ArrayList<>();
        for(AssetDTO assetDTO: assetDTOs.getAssetList()) {
            Asset asset = assetMapper.assetDTOToAsset(assetDTO);
//
//            AssetDTO newAssetDTO = saveNewAsset(asset, assetDTOs.getParentId());
//            response.add(newAssetDTO);

            findAndCopyChildren(asset, assetDTOs.getParentId(), response);
        }
        assetDTOs.setAssetList(response);
        return assetDTOs;
    }

    private AssetDTO saveNewAsset(Asset asset, Long parentId) {
        Asset newAssetEntity = new Asset();

        BeanUtils.copyProperties(asset, newAssetEntity);
        newAssetEntity.setWorkOrders(null);
        newAssetEntity.setMaps(null);
        newAssetEntity.setOtherFiles(null);

        newAssetEntity.setParentId(parentId);
        newAssetEntity.setId(null);
        newAssetEntity.setName(asset.getName() + "_NEW");
        Asset newAsset = assetRepository.save(newAssetEntity);
        AssetDTO assetDTO = assetMapper.assetToAssetDTO(newAsset);
        return assetDTO;
    }

    private void findAndCopyChildren(Asset asset, Long parentId, List<AssetDTO> response) {

        AssetDTO newAssetDTO = saveNewAsset(asset, parentId);
        response.add(newAssetDTO);

        Long assetId = asset.getId();
        Asset assetExample = new Asset();
        assetExample.setParentId(assetId);
        List<Asset> allChileAssets = assetRepository.findAll(Example.of(assetExample));
        for(Asset child: allChileAssets) {
            findAndCopyChildren(child, newAssetDTO.getId(), response);
        }
    }

    /**
     * Save all assets.
     *
     * @param assetDTOs the entity to save
     * @return the persisted entity
     */
    public AssetDTOs saveAll(AssetDTOs assetDTOs) {
        log.debug("Request to save Assets : {}", assetDTOs);
        List<AssetDTO> response = new ArrayList<>();
        int i=0;
        Asset lastUpdatedAsset = null;
        for(AssetDTO assetDTO: assetDTOs.getAssetList()) {
            Asset asset = assetMapper.assetDTOToAsset(assetDTO);
            Long parentId = assetDTOs.getParentId();
            if (i==0) {
                asset.setParentId(parentId);
            }
            else {
                asset.setParentId(lastUpdatedAsset.getId());
            }

            asset = assetRepository.save(asset);
            AssetDTO assetToAssetDTO = assetMapper.assetToAssetDTO(asset);
            response.add(assetToAssetDTO);
            lastUpdatedAsset = asset;
            i++;
        }
        assetDTOs.setAssetList(response);
        return assetDTOs;
    }

    /**
     *  Get all the assets.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AssetDTO> findAll() {
        log.debug("Request to get all Assets");
        List<AssetDTO> result = assetRepository.findAll().stream()
            .map(assetMapper::assetToAssetDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one asset by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AssetDTO findOne(Long id) {
        log.debug("Request to get Asset : {}", id);
        Asset asset = assetRepository.findOne(id);
        AssetDTO assetDTO = assetMapper.assetToAssetDTO(asset);
        return assetDTO;
    }

    /**
     *  Delete the  asset by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Asset : {}", id);
        assetRepository.delete(id);
    }
}
