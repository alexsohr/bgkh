package com.bgkh.service.impl;

import com.bgkh.domain.*;
import com.bgkh.repository.*;
import com.bgkh.service.AssetService;
import com.bgkh.domain.enumeration.AssetType;
import com.bgkh.service.dto.AssetDTO;
import com.bgkh.service.dto.AssetDTOs;
import com.bgkh.service.dto.AssetSpecificationTypeDataDTO;
import com.bgkh.service.mapper.AssetMapper;
import com.bgkh.service.mapper.AssetSpecificationTypeFieldMapper;
import com.bgkh.service.mapper.AssetSpecificationTypeValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;
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

    @Inject
    private UploadFileRepository uploadFileRepository;

    /**
     * Save a asset.
     *
     * @param assetDTO the entity to save
     * @return the persisted entity
     */
    public AssetDTO save(AssetDTO assetDTO) {
        log.debug("Request to save Asset : {}", assetDTO);
        Asset asset = assetMapper.assetDTOToAsset(assetDTO);
        attachUploadFilesFromIds(asset);
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

    private void findAndCopyChildren(Asset asset, Long parentId, List<AssetDTO> response) {


        AssetDTO newAssetDTO = saveNewAsset(asset, parentId);
        response.add(newAssetDTO);

        List<Asset> allChildAssets = findAllByParentId(asset.getId());
        for (Asset child : allChildAssets) {
            findAndCopyChildren(child, newAssetDTO.getId(), response);
        }
    }

    private AssetDTO saveNewAsset(Asset asset, Long parentId) {
        List<AssetSpecificationTypeValue> allByAssetId = assetSpecificationTypeValueRepository.findAllByAssetId(asset.getId());
        Asset newAssetEntity = new Asset();

        BeanUtils.copyProperties(asset, newAssetEntity);

        newAssetEntity.setParentId(parentId);
        newAssetEntity.setId(null);
        newAssetEntity.setName(asset.getName() + "_NEW");
        newAssetEntity.setMaps(null);
        newAssetEntity.setOtherFiles(null);
        newAssetEntity.setAssetSpecificationTypeValues(null);
        Asset newAsset = assetRepository.save(newAssetEntity);
        List<AssetSpecificationTypeValue> assetSpecificationTypeValues = new ArrayList<>();
        for (AssetSpecificationTypeValue typeValue : allByAssetId) {
            AssetSpecificationTypeValue assetSpecificationTypeValue = new AssetSpecificationTypeValue();
            BeanUtils.copyProperties(typeValue, assetSpecificationTypeValue);
            assetSpecificationTypeValue.setId(null);
            assetSpecificationTypeValue.setAsset(newAsset);
            assetSpecificationTypeValues.add(assetSpecificationTypeValue);
        }
        if (assetSpecificationTypeValues.size() > 0)
        assetSpecificationTypeValueRepository.save(assetSpecificationTypeValues);
        AssetDTO assetDTO = assetMapper.assetToAssetDTO(newAsset);
        return assetDTO;
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
            assetSpecificationTypeField.setCapacityUnit(specificationTypeDataDTO.getCapacityUnit());
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

                attachUploadFilesFromIds(asset);
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


    private void attachUploadFilesFromIds(Asset asset) {
        Iterator<UploadFile> iterator = asset.getMaps().iterator();
        Set<UploadFile> maps = new HashSet<>();
        while(iterator.hasNext()) {
            UploadFile uploadFile = iterator.next();
            Example<UploadFile> uploadFileExample = Example.of(uploadFile);
            UploadFile one = uploadFileRepository.findOne(uploadFileExample);
            maps.add(one);
        }
        asset.setMaps(maps);
        iterator = asset.getOtherFiles().iterator();
        Set<UploadFile> otherFiles = new HashSet<>();
        while(iterator.hasNext()) {
            UploadFile uploadFile = iterator.next();
            Example<UploadFile> uploadFileExample = Example.of(uploadFile);
            UploadFile one = uploadFileRepository.findOne(uploadFileExample);
            otherFiles.add(one);
        }
        asset.setOtherFiles(otherFiles);
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
    public List<String> findAllCapacityUnits() {
        List<String> allCapacityUnits = assetRepository.findAllCapacityUnits();
        List<String> fieldAllCapacityUnits = assetSpecificationTypeFieldRepository.findAllCapacityUnits();
        Set<String> capacities = new HashSet<>(allCapacityUnits);
        capacities.addAll(fieldAllCapacityUnits);
        allCapacityUnits = new ArrayList<>(capacities);
        return allCapacityUnits;
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

    private void findAllChildren(Long id, List<Asset> assets) {
        List<Asset> allByParentId = assetRepository.findAllByParentId(id);
        assets.addAll(allByParentId);
        for (Asset asset: allByParentId) {
            findAllChildren(asset.getId(), assets);
        }
    }
    /**
     * Delete the  asset by id.
     *
     * @param id the id of the entity
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Asset : {}", id);
        Asset asset = assetRepository.findOne(id);
        List<Asset> assets = new ArrayList<>();
        findAllChildren(id, assets);
        assets.add(asset);
        log.debug("*********************************************");
        log.debug(assets.toString());
        log.debug("*********************************************");
        assetRepository.delete(assets);
    }

    @Override
    public List<Asset> findAllByParentId(long id) {
        return assetRepository.findAllByParentId(id);
    }

    @Override
    public int findCountByParentId(long id) {
        return assetRepository.findCountByParentId(id);
    }


}
