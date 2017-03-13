package com.bgkh.service.impl;

import com.bgkh.domain.Asset;
import com.bgkh.domain.AssetSpecificationType;
import com.bgkh.domain.AssetSpecificationTypeField;
import com.bgkh.domain.AssetSpecificationTypeValue;
import com.bgkh.domain.enumeration.AssetType;
import com.bgkh.repository.AssetRepository;
import com.bgkh.repository.AssetSpecificationTypeFieldRepository;
import com.bgkh.repository.AssetSpecificationTypeRepository;
import com.bgkh.repository.AssetSpecificationTypeValueRepository;
import com.bgkh.service.AssetService;
import com.bgkh.service.dto.AssetDTO;
import com.bgkh.service.dto.AssetDTOs;
import com.bgkh.service.dto.AssetSpecificationTypeDataDTO;
import com.bgkh.service.dto.AssetSpecificationTypeFieldDTO;
import com.bgkh.service.mapper.AssetMapper;
import com.bgkh.service.mapper.AssetSpecificationTypeFieldMapper;
import com.bgkh.service.mapper.AssetSpecificationTypeValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Asset.
 */
@Service
@Transactional
public class AssetServiceImpl implements AssetService {

    private final Logger log = LoggerFactory.getLogger(AssetServiceImpl.class);

    @Inject
    private AssetRepository assetRepository;

    @Inject
    private AssetSpecificationTypeRepository assetSpecificationTypeRepository;

    @Inject
    private AssetSpecificationTypeFieldRepository assetSpecificationTypeFieldRepository;

    @Inject
    private AssetSpecificationTypeValueRepository assetSpecificationTypeValueRepository;


    @Inject
    private AssetMapper assetMapper;

    @Inject
    private AssetSpecificationTypeValueMapper assetSpecificationTypeValueMapper;

    @Inject
    private AssetSpecificationTypeFieldMapper assetSpecificationTypeFieldMapper;

    /**
     * Save a asset.
     *
     * @param assetDTO the entity to save
     * @return the persisted entity
     */
    public AssetDTO save(AssetDTO assetDTO) {
        log.debug("Request to save Asset : {}", assetDTO);
        Asset asset = assetMapper.assetDTOToAsset(assetDTO);
        saveOrUpdateSpecificType(asset, assetDTO);
        asset = assetRepository.save(asset);
//        saveOrUpdateSpecificType(asset, assetDTO);
        saveOrUpdateSpecificTypeFieldsAndValues(asset, assetDTO);
        AssetDTO result = assetMapper.assetToAssetDTO(asset);
        return result;
    }


    private void saveOrUpdateSpecificType(Asset asset, AssetDTO assetDTO) {
        if (!asset.getAssetType().equals(AssetType.ASSET_GROUP)) {
            AssetSpecificationType assetSpecificationType = new AssetSpecificationType();
            if (assetDTO.getAssetSpecificationTypeId() != null) {
                assetSpecificationType = assetSpecificationTypeRepository.findOne(assetDTO.getAssetSpecificationTypeId());
            } else {
                assetSpecificationType.setName(assetDTO.getAssetSpecificationTypeName());
            }
            assetSpecificationType = assetSpecificationTypeRepository.save(assetSpecificationType);
            asset.setAssetSpecificationType(assetSpecificationType);
        }
    }

    @Override
    @Transactional
    public AssetDTOs copy(AssetDTOs assetDTOs) {
        log.debug("Request to copy Assets : {}", assetDTOs);
        ArrayList<AssetDTO> response = new ArrayList<>();
        for (AssetDTO assetDTO : assetDTOs.getAssetList()) {
            Asset asset = assetMapper.assetDTOToAsset(assetDTO);
            findAndCopyChildren(asset, assetDTOs.getParentId(), response);
        }
        assetDTOs.setAssetList(response);
        return assetDTOs;
    }

    private AssetDTO saveNewAsset(Asset asset, Long parentId) {
        Asset newAssetEntity = new Asset();

        BeanUtils.copyProperties(asset, newAssetEntity);
        newAssetEntity.setWorkOrders(null);

        newAssetEntity.setParentId(parentId);
        newAssetEntity.setId(null);
        newAssetEntity.setName(asset.getName() + "_NEW");
        newAssetEntity.setMaps(null);
        newAssetEntity.setOtherFiles(null);
        Asset newAsset = assetRepository.save(newAssetEntity);
        List<AssetSpecificationTypeValue> assetSpecificationTypeValues = new ArrayList<>();
        List<AssetSpecificationTypeValue> allByAssetId = assetSpecificationTypeValueRepository.findAllByAssetId(asset.getId());
        for (AssetSpecificationTypeValue typeValue : allByAssetId) {
            AssetSpecificationTypeValue assetSpecificationTypeValue = new AssetSpecificationTypeValue();
            BeanUtils.copyProperties(typeValue, assetSpecificationTypeValue);
            assetSpecificationTypeValue.setId(null);
            assetSpecificationTypeValue.setAsset(newAsset);
            assetSpecificationTypeValues.add(assetSpecificationTypeValue);
        }
        assetSpecificationTypeValueRepository.save(assetSpecificationTypeValues);
        AssetDTO assetDTO = assetMapper.assetToAssetDTO(newAsset);
        return assetDTO;
    }

    private void findAndCopyChildren(Asset asset, Long parentId, List<AssetDTO> response) {

        AssetDTO newAssetDTO = saveNewAsset(asset, parentId);
        response.add(newAssetDTO);

        List<Asset> allChileAssets = assetRepository.findAllByParentId(asset.getId());
        for (Asset child : allChileAssets) {
            findAndCopyChildren(child, newAssetDTO.getId(), response);
        }
    }

    private void saveOrUpdateSpecificTypeFieldsAndValues(Asset asset, AssetDTO assetDTO) {

        List<AssetSpecificationTypeValue> allByAssetId = assetSpecificationTypeValueRepository.findAllByAssetId(asset.getId());
        if (allByAssetId != null) {
            assetSpecificationTypeValueRepository.delete(allByAssetId);
        }

        Iterator<AssetSpecificationTypeDataDTO> iterator = assetDTO.getAssetSpecificationTypeData().iterator();
        while (iterator.hasNext()) {
            AssetSpecificationTypeDataDTO specificationTypeDataDTO = iterator.next();
            //Saving AssetSpecificationTypeField
            AssetSpecificationTypeField assetSpecificationTypeField = new AssetSpecificationTypeField();
            if (specificationTypeDataDTO.getFieldId() != null) {
                assetSpecificationTypeField = assetSpecificationTypeFieldRepository.findOne(specificationTypeDataDTO.getFieldId());
            }
            assetSpecificationTypeField.setFieldLabel(specificationTypeDataDTO.getFieldLabel());
            assetSpecificationTypeField.setFieldType(specificationTypeDataDTO.getFieldType());
            assetSpecificationTypeField.setFieldName(specificationTypeDataDTO.getFieldLabel());
            assetSpecificationTypeField.setAssetSpecificationType(asset.getAssetSpecificationType());
            assetSpecificationTypeField = assetSpecificationTypeFieldRepository.save(assetSpecificationTypeField);

            //Saving AssetSpecificationTypeValue
            AssetSpecificationTypeValue assetSpecificationTypeValue = new AssetSpecificationTypeValue();
            assetSpecificationTypeValue.setAssetSpecificationTypeField(assetSpecificationTypeField);
            assetSpecificationTypeValue.setAsset(asset);
            assetSpecificationTypeValue.setFieldValue(specificationTypeDataDTO.getFieldValue());
            assetSpecificationTypeValueRepository.save(assetSpecificationTypeValue);
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
        int i = 0;
        Asset lastUpdatedAsset = null;
        for (AssetDTO assetDTO : assetDTOs.getAssetList()) {
            if (assetDTO.getName() != null) {
                Asset asset = assetMapper.assetDTOToAsset(assetDTO);
                Long parentId = assetDTOs.getParentId();
                if (i == 0) {
                    asset.setParentId(parentId);
                } else {
                    asset.setParentId(lastUpdatedAsset.getId());
                }

                saveOrUpdateSpecificType(asset, assetDTO);
                asset = assetRepository.save(asset);
                saveOrUpdateSpecificTypeFieldsAndValues(asset, assetDTO);
                AssetDTO assetToAssetDTO = assetMapper.assetToAssetDTO(asset);
                response.add(assetToAssetDTO);
                lastUpdatedAsset = asset;
            }
            i++;
        }
        assetDTOs.setAssetList(response);
        return assetDTOs;
    }

    /**
     * Get all the assets.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AssetDTO> findAll() {
        log.debug("Request to get all Assets");
        List<AssetDTO> result = assetRepository.findAllWithEagerRelationships().stream()
            .map(assetMapper::assetToAssetDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    @Override
    public List<String> findAllManufactures() {
        List<String> allManufactures = assetRepository.findAllManufactures();
        return allManufactures;
    }

    @Override
    public List<String> findAllAssetNames() {
        List<String> allManufactures = assetRepository.findAllAssetNames();
        return allManufactures;
    }

    @Override
    public List<String> findAllAssetLocations() {
        List<String> allManufactures = assetRepository.findAllAssetLocations();
        return allManufactures;
    }

    /**
     * Get one asset by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AssetDTO findOne(Long id) {
        log.debug("Request to get Asset : {}", id);
        Asset asset = assetRepository.findOneWithEagerRelationships(id);
        AssetDTO assetDTO = assetMapper.assetToAssetDTO(asset);
        return assetDTO;
    }

    /**
     * Delete the  asset by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Asset : {}", id);
        assetRepository.delete(id);
    }
}
