package com.bgkh.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alex on 11/26/17.
 */
public class WorkOrderDTOs implements Serializable {

    private List<WorkOrderDTO> workOrders;
    private Long assetId;

    public List<WorkOrderDTO> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(List<WorkOrderDTO> workOrders) {
        this.workOrders = workOrders;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    @Override
    public String toString() {
        return "WorkOrderDTOs{" +
            "workOrders=" + workOrders +
            ", assetId=" + assetId +
            '}';
    }
}
