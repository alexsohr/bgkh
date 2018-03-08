package com.bgkh.service.impl;

import com.bgkh.domain.WorkOrderHistory;
import com.bgkh.domain.WorkOrderSchedule;
import com.bgkh.domain.enumeration.HistoryStatus;
import com.bgkh.domain.enumeration.ScheduleStatus;
import com.bgkh.repository.WorkOrderHistoryRepository;
import com.bgkh.repository.WorkOrderScheduleRepository;
import com.bgkh.service.WorkOrderHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZonedDateTime;

/**
 * Created by alex on 3/6/18.
 */
@Service
@Transactional
public class WorkOrderHistoryServiceImpl implements WorkOrderHistoryService {


    private final Logger log = LoggerFactory.getLogger(WorkOrderHistoryServiceImpl.class);

    @Inject
    private WorkOrderHistoryRepository workOrderHistoryRepository;
    @Inject
    private WorkOrderScheduleRepository workOrderScheduleRepository;

    @Override
    public WorkOrderHistory save(WorkOrderHistory workOrderHistory) {
        WorkOrderSchedule workOrderSchedule = workOrderScheduleRepository.getOne(workOrderHistory.getWorkOrderSchedule().getId());
        workOrderHistory.setCreateDate(ZonedDateTime.now());
        if (workOrderHistory.getHistoryStatus().equals(HistoryStatus.COMPLETED)) {
            workOrderSchedule.setScheduleStatus(ScheduleStatus.COMPLETED);
            workOrderSchedule.setCompletedDate(ZonedDateTime.now());
        } else {
            workOrderSchedule.setScheduleStatus(ScheduleStatus.IN_PROGRESS);
        }
        workOrderScheduleRepository.save(workOrderSchedule);
        workOrderHistory.setWorkOrderSchedule(workOrderSchedule);
        return workOrderHistoryRepository.save(workOrderHistory);
    }
}
