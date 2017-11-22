package com.bgkh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bgkh.service.WorkOrderTemplateService;
import com.bgkh.web.rest.util.HeaderUtil;
import com.bgkh.web.rest.util.PaginationUtil;
import com.bgkh.service.dto.WorkOrderTemplateDTO;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing WorkOrderTemplate.
 */
@RestController
@RequestMapping("/api")
public class WorkOrderTemplateResource {

    private final Logger log = LoggerFactory.getLogger(WorkOrderTemplateResource.class);
        
    @Inject
    private WorkOrderTemplateService workOrderTemplateService;

    /**
     * POST  /work-order-templates : Create a new workOrderTemplate.
     *
     * @param workOrderTemplateDTO the workOrderTemplateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workOrderTemplateDTO, or with status 400 (Bad Request) if the workOrderTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-order-templates")
    @Timed
    public ResponseEntity<WorkOrderTemplateDTO> createWorkOrderTemplate(@Valid @RequestBody WorkOrderTemplateDTO workOrderTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save WorkOrderTemplate : {}", workOrderTemplateDTO);
        if (workOrderTemplateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("workOrderTemplate", "idexists", "A new workOrderTemplate cannot already have an ID")).body(null);
        }
        WorkOrderTemplateDTO result = workOrderTemplateService.save(workOrderTemplateDTO);
        return ResponseEntity.created(new URI("/api/work-order-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("workOrderTemplate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-order-templates : Updates an existing workOrderTemplate.
     *
     * @param workOrderTemplateDTO the workOrderTemplateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workOrderTemplateDTO,
     * or with status 400 (Bad Request) if the workOrderTemplateDTO is not valid,
     * or with status 500 (Internal Server Error) if the workOrderTemplateDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-order-templates")
    @Timed
    public ResponseEntity<WorkOrderTemplateDTO> updateWorkOrderTemplate(@Valid @RequestBody WorkOrderTemplateDTO workOrderTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update WorkOrderTemplate : {}", workOrderTemplateDTO);
        if (workOrderTemplateDTO.getId() == null) {
            return createWorkOrderTemplate(workOrderTemplateDTO);
        }
        WorkOrderTemplateDTO result = workOrderTemplateService.save(workOrderTemplateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("workOrderTemplate", workOrderTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-order-templates : get all the workOrderTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of workOrderTemplates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/work-order-templates")
    @Timed
    public ResponseEntity<List<WorkOrderTemplateDTO>> getAllWorkOrderTemplates(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WorkOrderTemplates");
        Page<WorkOrderTemplateDTO> page = workOrderTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-order-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /work-order-templates/:id : get the "id" workOrderTemplate.
     *
     * @param id the id of the workOrderTemplateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workOrderTemplateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/work-order-templates/{id}")
    @Timed
    public ResponseEntity<WorkOrderTemplateDTO> getWorkOrderTemplate(@PathVariable Long id) {
        log.debug("REST request to get WorkOrderTemplate : {}", id);
        WorkOrderTemplateDTO workOrderTemplateDTO = workOrderTemplateService.findOne(id);
        return Optional.ofNullable(workOrderTemplateDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /work-order-templates/:id : delete the "id" workOrderTemplate.
     *
     * @param id the id of the workOrderTemplateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-order-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkOrderTemplate(@PathVariable Long id) {
        log.debug("REST request to delete WorkOrderTemplate : {}", id);
        workOrderTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("workOrderTemplate", id.toString())).build();
    }

}
