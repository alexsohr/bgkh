package com.bgkh.service;

import com.bgkh.domain.WorkOrderTemplate;
import com.bgkh.repository.WorkOrderTemplateRepository;
import com.bgkh.service.dto.WorkOrderTemplateDTO;
import com.bgkh.service.mapper.WorkOrderTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing WorkOrderTemplate.
 */
@Service
@Transactional
public class WorkOrderTemplateService {

    private final Logger log = LoggerFactory.getLogger(WorkOrderTemplateService.class);

    @Inject
    private WorkOrderTemplateRepository workOrderTemplateRepository;

    @Inject
    private WorkOrderTemplateMapper workOrderTemplateMapper;

    /**
     * Save a workOrderTemplate.
     *
     * @param workOrderTemplateDTO the entity to save
     * @return the persisted entity
     */
    public WorkOrderTemplateDTO save(WorkOrderTemplateDTO workOrderTemplateDTO) {
        log.debug("Request to save WorkOrderTemplate : {}", workOrderTemplateDTO);
        WorkOrderTemplate workOrderTemplate = workOrderTemplateMapper.workOrderTemplateDTOToWorkOrderTemplate(workOrderTemplateDTO);
        workOrderTemplate = workOrderTemplateRepository.save(workOrderTemplate);
        WorkOrderTemplateDTO result = workOrderTemplateMapper.workOrderTemplateToWorkOrderTemplateDTO(workOrderTemplate);
        return result;
    }

    /**
     *  Get all the workOrderTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkOrderTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all paged WorkOrderTemplates");
        Page<WorkOrderTemplate> result = workOrderTemplateRepository.findAll(pageable);
        return result.map(workOrderTemplate -> workOrderTemplateMapper.workOrderTemplateToWorkOrderTemplateDTO(workOrderTemplate));
    }

    /**
     *  Get all the workOrderTemplates.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WorkOrderTemplateDTO> findAll() {
        log.debug("Request to get all WorkOrderTemplates");
        List<WorkOrderTemplate> result = workOrderTemplateRepository.findAll();
        return result.stream().map(workOrderTemplateMapper::workOrderTemplateToWorkOrderTemplateDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one workOrderTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public WorkOrderTemplateDTO findOne(Long id) {
        log.debug("Request to get WorkOrderTemplate : {}", id);
        WorkOrderTemplate workOrderTemplate = workOrderTemplateRepository.findOne(id);
        WorkOrderTemplateDTO workOrderTemplateDTO = workOrderTemplateMapper.workOrderTemplateToWorkOrderTemplateDTO(workOrderTemplate);
        return workOrderTemplateDTO;
    }

    /**
     *  Delete the  workOrderTemplate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkOrderTemplate : {}", id);
        workOrderTemplateRepository.delete(id);
    }

    public List<WorkOrderTemplateDTO> findAllByAssetTypeId(Long assetTypeId) {
        return workOrderTemplateRepository.findAllByAssetTypeId(assetTypeId).stream()
            .map(workOrderTemplateMapper::workOrderTemplateToWorkOrderTemplateDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
