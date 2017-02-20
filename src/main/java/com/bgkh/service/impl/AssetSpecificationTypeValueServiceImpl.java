package com.bgkh.service.impl;

import com.bgkh.service.AssetSpecificationTypeValueService;
import com.bgkh.domain.AssetSpecificationTypeValue;
import com.bgkh.repository.AssetSpecificationTypeValueRepository;
import com.bgkh.service.dto.AssetSpecificationTypeValueDTO;
import com.bgkh.service.mapper.AssetSpecificationTypeValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AssetSpecificationTypeValue.
 */
@Service
@Transactional
public class AssetSpecificationTypeValueServiceImpl implements AssetSpecificationTypeValueService{

    private final Logger log = LoggerFactory.getLogger(AssetSpecificationTypeValueServiceImpl.class);
    
    @Inject
    private AssetSpecificationTypeValueRepository assetSpecificationTypeValueRepository;

    @Inject
    private AssetSpecificationTypeValueMapper assetSpecificationTypeValueMapper;

    /**
     * Save a assetSpecificationTypeValue.
     *
     * @param assetSpecificationTypeValueDTO the entity to save
     * @return the persisted entity
     */
    public AssetSpecificationTypeValueDTO save(AssetSpecificationTypeValueDTO assetSpecificationTypeValueDTO) {
        log.debug("Request to save AssetSpecificationTypeValue : {}", assetSpecificationTypeValueDTO);
        AssetSpecificationTypeValue assetSpecificationTypeValue = assetSpecificationTypeValueMapper.assetSpecificationTypeValueDTOToAssetSpecificationTypeValue(assetSpecificationTypeValueDTO);
        assetSpecificationTypeValue = assetSpecificationTypeValueRepository.save(assetSpecificationTypeValue);
        AssetSpecificationTypeValueDTO result = assetSpecificationTypeValueMapper.assetSpecificationTypeValueToAssetSpecificationTypeValueDTO(assetSpecificationTypeValue);
        return result;
    }

    /**
     *  Get all the assetSpecificationTypeValues.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AssetSpecificationTypeValueDTO> findAll() {
        log.debug("Request to get all AssetSpecificationTypeValues");
        List<AssetSpecificationTypeValueDTO> result = assetSpecificationTypeValueRepository.findAll().stream()
            .map(assetSpecificationTypeValueMapper::assetSpecificationTypeValueToAssetSpecificationTypeValueDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one assetSpecificationTypeValue by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AssetSpecificationTypeValueDTO findOne(Long id) {
        log.debug("Request to get AssetSpecificationTypeValue : {}", id);
        AssetSpecificationTypeValue assetSpecificationTypeValue = assetSpecificationTypeValueRepository.findOne(id);
        AssetSpecificationTypeValueDTO assetSpecificationTypeValueDTO = assetSpecificationTypeValueMapper.assetSpecificationTypeValueToAssetSpecificationTypeValueDTO(assetSpecificationTypeValue);
        return assetSpecificationTypeValueDTO;
    }

    /**
     *  Delete the  assetSpecificationTypeValue by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AssetSpecificationTypeValue : {}", id);
        assetSpecificationTypeValueRepository.delete(id);
    }
}
