package com.bgkh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bgkh.service.WorkOrderService;
import com.bgkh.web.rest.util.HeaderUtil;
import com.bgkh.web.rest.util.PaginationUtil;
import com.bgkh.service.dto.WorkOrderDTO;

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
 * REST controller for managing WorkOrder.
 */
@RestController
@RequestMapping("/api")
public class WorkOrderResource {

    private final Logger log = LoggerFactory.getLogger(WorkOrderResource.class);
        
    @Inject
    private WorkOrderService workOrderService;

    /**
     * POST  /work-orders : Create a new workOrder.
     *
     * @param workOrderDTO the workOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workOrderDTO, or with status 400 (Bad Request) if the workOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-orders")
    @Timed
    public ResponseEntity<WorkOrderDTO> createWorkOrder(@Valid @RequestBody WorkOrderDTO workOrderDTO) throws URISyntaxException {
        log.debug("REST request to save WorkOrder : {}", workOrderDTO);
        if (workOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("workOrder", "idexists", "A new workOrder cannot already have an ID")).body(null);
        }
        WorkOrderDTO result = workOrderService.save(workOrderDTO);
        return ResponseEntity.created(new URI("/api/work-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("workOrder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-orders : Updates an existing workOrder.
     *
     * @param workOrderDTO the workOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workOrderDTO,
     * or with status 400 (Bad Request) if the workOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the workOrderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-orders")
    @Timed
    public ResponseEntity<WorkOrderDTO> updateWorkOrder(@Valid @RequestBody WorkOrderDTO workOrderDTO) throws URISyntaxException {
        log.debug("REST request to update WorkOrder : {}", workOrderDTO);
        if (workOrderDTO.getId() == null) {
            return createWorkOrder(workOrderDTO);
        }
        WorkOrderDTO result = workOrderService.save(workOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("workOrder", workOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-orders : get all the workOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of workOrders in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/work-orders")
    @Timed
    public ResponseEntity<List<WorkOrderDTO>> getAllWorkOrders(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WorkOrders");
        Page<WorkOrderDTO> page = workOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /work-orders/:id : get the "id" workOrder.
     *
     * @param id the id of the workOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/work-orders/{id}")
    @Timed
    public ResponseEntity<WorkOrderDTO> getWorkOrder(@PathVariable Long id) {
        log.debug("REST request to get WorkOrder : {}", id);
        WorkOrderDTO workOrderDTO = workOrderService.findOne(id);
        return Optional.ofNullable(workOrderDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /work-orders/:id : delete the "id" workOrder.
     *
     * @param id the id of the workOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable Long id) {
        log.debug("REST request to delete WorkOrder : {}", id);
        workOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("workOrder", id.toString())).build();
    }

}
