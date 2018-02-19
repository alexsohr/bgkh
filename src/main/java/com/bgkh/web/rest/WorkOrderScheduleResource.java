package com.bgkh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bgkh.domain.WorkOrderSchedule;
import com.bgkh.service.WorkOrderScheduleService;
import com.bgkh.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WorkOrderSchedule.
 */
@RestController
@RequestMapping("/api")
public class WorkOrderScheduleResource {

    private final Logger log = LoggerFactory.getLogger(WorkOrderScheduleResource.class);
        
    @Inject
    private WorkOrderScheduleService workOrderScheduleService;

    /**
     * POST  /work-order-schedules : Create a new workOrderSchedule.
     *
     * @param workOrderSchedule the workOrderSchedule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workOrderSchedule, or with status 400 (Bad Request) if the workOrderSchedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-order-schedules")
    @Timed
    public ResponseEntity<WorkOrderSchedule> createWorkOrderSchedule(@RequestBody WorkOrderSchedule workOrderSchedule) throws URISyntaxException {
        log.debug("REST request to save WorkOrderSchedule : {}", workOrderSchedule);
        if (workOrderSchedule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("workOrderSchedule", "idexists", "A new workOrderSchedule cannot already have an ID")).body(null);
        }
        WorkOrderSchedule result = workOrderScheduleService.save(workOrderSchedule);
        return ResponseEntity.created(new URI("/api/work-order-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("workOrderSchedule", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-order-schedules : Updates an existing workOrderSchedule.
     *
     * @param workOrderSchedule the workOrderSchedule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workOrderSchedule,
     * or with status 400 (Bad Request) if the workOrderSchedule is not valid,
     * or with status 500 (Internal Server Error) if the workOrderSchedule couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-order-schedules")
    @Timed
    public ResponseEntity<WorkOrderSchedule> updateWorkOrderSchedule(@RequestBody WorkOrderSchedule workOrderSchedule) throws URISyntaxException {
        log.debug("REST request to update WorkOrderSchedule : {}", workOrderSchedule);
        if (workOrderSchedule.getId() == null) {
            return createWorkOrderSchedule(workOrderSchedule);
        }
        WorkOrderSchedule result = workOrderScheduleService.save(workOrderSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("workOrderSchedule", workOrderSchedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-order-schedules : get all the workOrderSchedules.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workOrderSchedules in body
     */
    @GetMapping("/work-order-schedules")
    @Timed
    public List<WorkOrderSchedule> getAllWorkOrderSchedules() {
        log.debug("REST request to get all WorkOrderSchedules");
        return workOrderScheduleService.findAll();
    }

    /**
     * GET  /work-order-schedules/:id : get the "id" workOrderSchedule.
     *
     * @param id the id of the workOrderSchedule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workOrderSchedule, or with status 404 (Not Found)
     */
    @GetMapping("/work-order-schedules/{id}")
    @Timed
    public ResponseEntity<WorkOrderSchedule> getWorkOrderSchedule(@PathVariable Long id) {
        log.debug("REST request to get WorkOrderSchedule : {}", id);
        WorkOrderSchedule workOrderSchedule = workOrderScheduleService.findOne(id);
        return Optional.ofNullable(workOrderSchedule)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /work-order-schedules/:id : delete the "id" workOrderSchedule.
     *
     * @param id the id of the workOrderSchedule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-order-schedules/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkOrderSchedule(@PathVariable Long id) {
        log.debug("REST request to delete WorkOrderSchedule : {}", id);
        workOrderScheduleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("workOrderSchedule", id.toString())).build();
    }

}
