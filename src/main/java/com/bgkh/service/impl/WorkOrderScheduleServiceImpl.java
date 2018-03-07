package com.bgkh.service.impl;

import com.bgkh.domain.User;
import com.bgkh.domain.WorkOrderSchedule;
import com.bgkh.repository.WorkOrderScheduleRepository;
import com.bgkh.security.AuthoritiesConstants;
import com.bgkh.security.SecurityUtils;
import com.bgkh.service.UserService;
import com.bgkh.service.WorkOrderScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing WorkOrderSchedule.
 */
@Service
@Transactional
public class WorkOrderScheduleServiceImpl implements WorkOrderScheduleService{

    private final Logger log = LoggerFactory.getLogger(WorkOrderScheduleServiceImpl.class);

    @Inject
    private WorkOrderScheduleRepository workOrderScheduleRepository;
    @Inject
    private UserService userService;

    /**
     * Save a workOrderSchedule.
     *
     * @param workOrderSchedule the entity to save
     * @return the persisted entity
     */
    public WorkOrderSchedule save(WorkOrderSchedule workOrderSchedule) {
        log.debug("Request to save WorkOrderSchedule : {}", workOrderSchedule);
        return workOrderScheduleRepository.save(workOrderSchedule);
    }

    /**
     *  Get all the workOrderSchedules.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WorkOrderSchedule> findAll() {
        log.debug("Request to get all WorkOrderSchedules");
        return workOrderScheduleRepository.findAll();
    }

    /**
     *  Get all the workOrderSchedules.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WorkOrderSchedule> findAllByUser() {
        log.debug("Request to get all WorkOrderSchedulesByUser");
        String login = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(login);
        List<WorkOrderSchedule> result = null;
        if (user.isPresent()) {
            if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER)) {
                result = workOrderScheduleRepository.findAll();
            }
            else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
                result = workOrderScheduleRepository.findAllByUser(user.get().getId());
            }
            else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SUPERVISOR)) {
                result = workOrderScheduleRepository.findAllBySupervisorUser(user.get().getId());
            }
        }
        return result;
    }

    /**
     *  Get one workOrderSchedule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public WorkOrderSchedule findOne(Long id) {
        log.debug("Request to get WorkOrderSchedule : {}", id);
        return workOrderScheduleRepository.findOne(id);
    }

    /**
     *  Delete the  workOrderSchedule by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkOrderSchedule : {}", id);
        workOrderScheduleRepository.delete(id);
    }
}
