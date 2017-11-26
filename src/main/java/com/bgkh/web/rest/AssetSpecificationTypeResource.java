package com.bgkh.web.rest;

import com.bgkh.domain.Asset;
import com.bgkh.repository.AssetRepository;
import com.bgkh.service.AssetService;
import com.bgkh.service.dto.AssetDTO;
import com.codahale.metrics.annotation.Timed;
import com.bgkh.domain.AssetSpecificationType;

import com.bgkh.repository.AssetSpecificationTypeRepository;
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
import java.util.*;

/**
 * REST controller for managing AssetSpecificationType.
 */
@RestController
@RequestMapping("/api")
public class AssetSpecificationTypeResource {

    private final Logger log = LoggerFactory.getLogger(AssetSpecificationTypeResource.class);

    @Inject
    private AssetSpecificationTypeRepository assetSpecificationTypeRepository;

    @Inject
    private AssetService assetService;

    /**
     * POST  /asset-specification-types : Create a new assetSpecificationType.
     *
     * @param assetSpecificationType the assetSpecificationType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assetSpecificationType, or with status 400 (Bad Request) if the assetSpecificationType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/asset-specification-types")
    @Timed
    public ResponseEntity<AssetSpecificationType> createAssetSpecificationType(@Valid @RequestBody AssetSpecificationType assetSpecificationType) throws URISyntaxException {
        log.debug("REST request to save AssetSpecificationType : {}", assetSpecificationType);
        if (assetSpecificationType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("assetSpecificationType", "idexists", "A new assetSpecificationType cannot already have an ID")).body(null);
        }
        AssetSpecificationType result = assetSpecificationTypeRepository.save(assetSpecificationType);
        return ResponseEntity.created(new URI("/api/asset-specification-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetSpecificationType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /asset-specification-types : Updates an existing assetSpecificationType.
     *
     * @param assetSpecificationType the assetSpecificationType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assetSpecificationType,
     * or with status 400 (Bad Request) if the assetSpecificationType is not valid,
     * or with status 500 (Internal Server Error) if the assetSpecificationType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/asset-specification-types")
    @Timed
    public ResponseEntity<AssetSpecificationType> updateAssetSpecificationType(@Valid @RequestBody AssetSpecificationType assetSpecificationType) throws URISyntaxException {
        log.debug("REST request to update AssetSpecificationType : {}", assetSpecificationType);
        if (assetSpecificationType.getId() == null) {
            return createAssetSpecificationType(assetSpecificationType);
        }
        AssetSpecificationType result = assetSpecificationTypeRepository.save(assetSpecificationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetSpecificationType", assetSpecificationType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /asset-specification-types : get all the assetSpecificationTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of assetSpecificationTypes in body
     */
    @GetMapping("/asset-specification-types")
    @Timed
    public List<AssetSpecificationType> getAllAssetSpecificationTypes() {
        log.debug("REST request to get all AssetSpecificationTypes");
        List<AssetSpecificationType> assetSpecificationTypes = assetSpecificationTypeRepository.findAll();
        return assetSpecificationTypes;
    }


    /**
     * GET  /asset-specification-types : get all the assetSpecificationTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of assetSpecificationTypes in body
     */
    @GetMapping("/work-order-asset-specification-types")
    @Timed
    public List<AssetSpecificationType> getAllWorkOrderAssetSpecificationTypes() {
        log.debug("REST request to get all AssetSpecificationTypes for WorkOrder");
        List<AssetDTO> allByParentId = assetService.findAll();
        List<AssetSpecificationType> assetSpecificationTypes = assetSpecificationTypeRepository.findAllWorkOrderAssetSpecificationTypes();
        Set<AssetSpecificationType> resultSpecificationTypes = new HashSet<>();
        for (AssetDTO asset: allByParentId) {
            int childCount = assetService.findCountByParentId(asset.getId());
            if (childCount == 0) {
                for (AssetSpecificationType specificationType: assetSpecificationTypes) {
                    if (asset.getAssetSpecificationTypeId().equals(specificationType.getId())) {
                        resultSpecificationTypes.add(specificationType);
                        break;
                    }
                }
            }
        }
        assetSpecificationTypes = new ArrayList<>(resultSpecificationTypes);
        return assetSpecificationTypes;
    }

    /**
     * GET  /asset-specification-types/:id : get the "id" assetSpecificationType.
     *
     * @param id the id of the assetSpecificationType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assetSpecificationType, or with status 404 (Not Found)
     */
    @GetMapping("/asset-specification-types/{id}")
    @Timed
    public ResponseEntity<AssetSpecificationType> getAssetSpecificationType(@PathVariable Long id) {
        log.debug("REST request to get AssetSpecificationType : {}", id);
        AssetSpecificationType assetSpecificationType = assetSpecificationTypeRepository.findOne(id);
        return Optional.ofNullable(assetSpecificationType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /asset-specification-types/:id : delete the "id" assetSpecificationType.
     *
     * @param id the id of the assetSpecificationType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/asset-specification-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssetSpecificationType(@PathVariable Long id) {
        log.debug("REST request to delete AssetSpecificationType : {}", id);
        assetSpecificationTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetSpecificationType", id.toString())).build();
    }

}
