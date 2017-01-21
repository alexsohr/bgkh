package com.bgkh.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the Asset entity.
 */
public class AssetDTOs implements Serializable {

    @NotNull
    private List<AssetDTO> assetList;

    public List<AssetDTO> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<AssetDTO> assetList) {
        this.assetList = assetList;
    }
}
