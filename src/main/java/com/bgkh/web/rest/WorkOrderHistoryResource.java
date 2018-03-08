package com.bgkh.web.rest;

import com.bgkh.service.WorkOrderHistoryService;
import com.codahale.metrics.annotation.Timed;
import com.bgkh.domain.WorkOrderHistory;

import com.bgkh.repository.WorkOrderHistoryRepository;
import com.bgkh.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WorkOrderHistory.
 */
@RestController
@RequestMapping("/api")
public class WorkOrderHistoryResource {

    private final Logger log = LoggerFactory.getLogger(WorkOrderHistoryResource.class);

    @Inject
    private WorkOrderHistoryRepository workOrderHistoryRepository;

    @Inject
    private WorkOrderHistoryService workOrderHistoryService;

    /**
     * POST  /work-order-histories : Create a new workOrderHistory.
     *
     * @param workOrderHistory the workOrderHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workOrderHistory, or with status 400 (Bad Request) if the workOrderHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-order-histories")
    @Timed
    public ResponseEntity<WorkOrderHistory> createWorkOrderHistory(@Valid @RequestBody WorkOrderHistory workOrderHistory) throws URISyntaxException {
        log.debug("REST request to save WorkOrderHistory : {}", workOrderHistory);
        if (workOrderHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("workOrderHistory", "idexists", "A new workOrderHistory cannot already have an ID")).body(null);
        }
        WorkOrderHistory result = workOrderHistoryService.save(workOrderHistory);
        return ResponseEntity.created(new URI("/api/work-order-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("workOrderHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-order-histories : Updates an existing workOrderHistory.
     *
     * @param workOrderHistory the workOrderHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workOrderHistory,
     * or with status 400 (Bad Request) if the workOrderHistory is not valid,
     * or with status 500 (Internal Server Error) if the workOrderHistory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-order-histories")
    @Timed
    public ResponseEntity<WorkOrderHistory> updateWorkOrderHistory(@Valid @RequestBody WorkOrderHistory workOrderHistory) throws URISyntaxException {
        log.debug("REST request to update WorkOrderHistory : {}", workOrderHistory);
        if (workOrderHistory.getId() == null) {
            return createWorkOrderHistory(workOrderHistory);
        }
        WorkOrderHistory result = workOrderHistoryRepository.save(workOrderHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("workOrderHistory", workOrderHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-order-histories : get all the workOrderHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workOrderHistories in body
     */
    @GetMapping("/work-order-histories")
    @Timed
    public List<WorkOrderHistory> getAllWorkOrderHistories() {
        log.debug("REST request to get all WorkOrderHistories");
        List<WorkOrderHistory> workOrderHistories = workOrderHistoryRepository.findAll();
        return workOrderHistories;
    }

    /**
     * GET  /work-order-histories : get all the workOrderHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workOrderHistories in body
     */
    @GetMapping("/work-order-histories/asset/{id}")
    @Timed
    public List<WorkOrderHistory> getAllWorkOrderHistoriesByAssetId(@PathVariable Long id) {
        log.debug("REST request to get all getAllWorkOrderHistoriesByAssetId");
        List<WorkOrderHistory> workOrderHistories = workOrderHistoryRepository.findAllByAssetId(id);
        return workOrderHistories;
    }

    /**
     * GET  /work-order-histories/:id : get the "id" workOrderHistory.
     *
     * @param id the id of the workOrderHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workOrderHistory, or with status 404 (Not Found)
     */
    @GetMapping("/work-order-histories/{id}")
    @Timed
    public ResponseEntity<WorkOrderHistory> getWorkOrderHistory(@PathVariable Long id) {
        log.debug("REST request to get WorkOrderHistory : {}", id);
        WorkOrderHistory workOrderHistory = workOrderHistoryRepository.findOne(id);
        return Optional.ofNullable(workOrderHistory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /work-order-histories/:id : delete the "id" workOrderHistory.
     *
     * @param id the id of the workOrderHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-order-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkOrderHistory(@PathVariable Long id) {
        log.debug("REST request to delete WorkOrderHistory : {}", id);
        workOrderHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("workOrderHistory", id.toString())).build();
    }

}
