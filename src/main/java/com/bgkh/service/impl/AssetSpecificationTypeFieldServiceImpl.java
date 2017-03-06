package com.bgkh.service.impl;

import com.bgkh.domain.AssetSpecificationType;
import com.bgkh.domain.AssetSpecificationTypeField;
import com.bgkh.repository.AssetSpecificationTypeFieldRepository;
import com.bgkh.service.AssetSpecificationTypeFieldService;
import com.bgkh.service.dto.AssetSpecificationTypeFieldDTO;
import com.bgkh.service.mapper.AssetSpecificationTypeFieldMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AssetSpecificationTypeField.
 */
@Service
@Transactional
public class AssetSpecificationTypeFieldServiceImpl implements AssetSpecificationTypeFieldService{

    private final Logger log = LoggerFactory.getLogger(AssetSpecificationTypeFieldServiceImpl.class);

    @Inject
    private AssetSpecificationTypeFieldRepository assetSpecificationTypeFieldRepository;

    @Inject
    private AssetSpecificationTypeFieldMapper assetSpecificationTypeFieldMapper;

    /**
     * Save a assetSpecificationTypeField.
     *
     * @param assetSpecificationTypeFieldDTO the entity to save
     * @return the persisted entity
     */
    public AssetSpecificationTypeFieldDTO save(AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO) {
        log.debug("Request to save AssetSpecificationTypeField : {}", assetSpecificationTypeFieldDTO);
        AssetSpecificationTypeField assetSpecificationTypeField = assetSpecificationTypeFieldMapper.assetSpecificationTypeFieldDTOToAssetSpecificationTypeField(assetSpecificationTypeFieldDTO);
        assetSpecificationTypeField = assetSpecificationTypeFieldRepository.save(assetSpecificationTypeField);
        AssetSpecificationTypeFieldDTO result = assetSpecificationTypeFieldMapper.assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO(assetSpecificationTypeField);
        return result;
    }

    /**
     *  Get all the assetSpecificationTypeFields.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AssetSpecificationTypeFieldDTO> findAll() {
        log.debug("Request to get all AssetSpecificationTypeFields");
        List<AssetSpecificationTypeFieldDTO> result = assetSpecificationTypeFieldRepository.findAll().stream()
            .map(assetSpecificationTypeFieldMapper::assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    @Override
    public List<AssetSpecificationTypeFieldDTO> findAllByAssetSpecificationTypeId(Long id) {
        List<AssetSpecificationTypeFieldDTO> assetSpecificationTypeFields = assetSpecificationTypeFieldRepository.findAllByTypeId(id).stream()
            .map(assetSpecificationTypeFieldMapper::assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return assetSpecificationTypeFields;
    }

    /**
     *  Get one assetSpecificationTypeField by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AssetSpecificationTypeFieldDTO findOne(Long id) {
        log.debug("Request to get AssetSpecificationTypeField : {}", id);
        AssetSpecificationTypeField assetSpecificationTypeField = assetSpecificationTypeFieldRepository.findOne(id);
        AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO = assetSpecificationTypeFieldMapper.assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO(assetSpecificationTypeField);
        return assetSpecificationTypeFieldDTO;
    }

    /**
     *  Delete the  assetSpecificationTypeField by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AssetSpecificationTypeField : {}", id);
        assetSpecificationTypeFieldRepository.delete(id);
    }
}
