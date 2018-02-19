package com.bgkh.service.impl;

import com.bgkh.domain.WorkOrder;
import com.bgkh.domain.WorkOrderSchedule;
import com.bgkh.domain.WorkOrderTemplate;
import com.bgkh.domain.enumeration.WorkOrderTemplateFunctionType;
import com.bgkh.repository.WorkOrderRepository;
import com.bgkh.repository.WorkOrderScheduleRepository;
import com.bgkh.service.WorkOrderJobScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by alex on 2/18/18.
 */
@Service
@Transactional
public class WorkOrderJobScheduleServiceImpl implements WorkOrderJobScheduleService {

    @Inject
    private WorkOrderRepository workOrderRepository;

    @Inject
    private WorkOrderScheduleRepository workOrderScheduleRepository;

    @Override
    public void processWorkOrderSchedules() {
        List<WorkOrder> workOrders = workOrderRepository.findAll();
        for(WorkOrder workOrder: workOrders) {
            WorkOrderTemplate workOrderTemplate = workOrder.getWorkOrderTemplate();
//            workOrderScheduleRepository.findAllByWorkOrder(workOrder);
            if (workOrderTemplate.getFunctionType().equals(WorkOrderTemplateFunctionType.USAGE_HOURS)) {

            }
            else {

            }
        }
    }
}
