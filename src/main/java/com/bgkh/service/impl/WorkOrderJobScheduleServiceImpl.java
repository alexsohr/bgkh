package com.bgkh.service.impl;

import com.bgkh.domain.WorkOrder;
import com.bgkh.domain.WorkOrderSchedule;
import com.bgkh.domain.WorkOrderTemplate;
import com.bgkh.domain.enumeration.ScheduleStatus;
import com.bgkh.domain.enumeration.WorkOrderTemplateFunctionType;
import com.bgkh.repository.WorkOrderRepository;
import com.bgkh.repository.WorkOrderScheduleRepository;
import com.bgkh.service.WorkOrderJobScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

/**
 * Created by alex on 2/18/18.
 */
@Service
@Transactional
public class WorkOrderJobScheduleServiceImpl implements WorkOrderJobScheduleService {

    private final Logger logger = LoggerFactory.getLogger(WorkOrderJobScheduleServiceImpl.class);

    @Inject
    private WorkOrderRepository workOrderRepository;

    @Inject
    private WorkOrderScheduleRepository workOrderScheduleRepository;

    @Override
    public void processWorkOrderSchedules() {
        List<WorkOrder> workOrders = workOrderRepository.findAll();
        for(WorkOrder workOrder: workOrders) {
            WorkOrderTemplate workOrderTemplate = workOrder.getWorkOrderTemplate();
            List<WorkOrderSchedule> workOrderSchedules = workOrderScheduleRepository.findAllByWorkOrder(workOrder.getId(), workOrder.getAsset().getId(), workOrderTemplate.getId());
            WorkOrderSchedule lastWorkOrderSchedule = null;
            if (!workOrderSchedules.isEmpty()) {
                lastWorkOrderSchedule = workOrderSchedules.get(0);
            }
            if (lastWorkOrderSchedule == null || lastWorkOrderSchedule.getScheduleStatus().equals(ScheduleStatus.COMPLETED)) {
                processNewSchedule(workOrder, workOrderTemplate, lastWorkOrderSchedule);
            }
        }
    }

    private void processNewSchedule(WorkOrder workOrder, WorkOrderTemplate workOrderTemplate, WorkOrderSchedule lastWorkOrderSchedule) {
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();

        if (workOrderTemplate.getFunctionType().equals(WorkOrderTemplateFunctionType.USAGE_HOURS)) {
            if (lastWorkOrderSchedule == null) {
                createNewHourlyTemplate(workOrder);
            }

        } else {
            if (lastWorkOrderSchedule == null) {
                createNewDailyTemplate(workOrder);
            } else {
                calendar.setTime(java.sql.Date.valueOf(lastWorkOrderSchedule.getCompletedDate()));
                calendar.add(Calendar.DAY_OF_MONTH, workOrderTemplate.getNumberOfDays().intValue());
                long nextSchedule = calendar.getTimeInMillis();
                if (now >= nextSchedule) {
                    createNewDailyTemplate(workOrder);
                }
            }

        }
    }

    private void createNewDailyTemplate(WorkOrder workOrder) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, workOrder.getWorkOrderTemplate().getDueDays().intValue());

        WorkOrderSchedule workOrderSchedule = new WorkOrderSchedule();
        workOrderSchedule.setAsset(workOrder.getAsset());
        workOrderSchedule.setDescription(workOrder.getDescription());
        workOrderSchedule.setCreateDate(LocalDate.now());
        workOrderSchedule.setExpireDate(LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
        workOrderSchedule.setScheduleStatus(ScheduleStatus.NOT_STARTED);
        workOrderSchedule.setWorkOrder(workOrder);
        workOrderSchedule.setWorkOrderTemplate(workOrder.getWorkOrderTemplate());
        workOrderScheduleRepository.save(workOrderSchedule);
        logger.info("New schedule created for workOrder: {}", workOrder);
    }

    private void createNewHourlyTemplate(WorkOrder workOrder) {
        //TODO implement this method later
        logger.warn("Unable to create schedule for workOrder: {}" , workOrder);
    }
}
