package com.bgkh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bgkh.service.AssetSpecificationTypeValueService;
import com.bgkh.web.rest.util.HeaderUtil;
import com.bgkh.service.dto.AssetSpecificationTypeValueDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing AssetSpecificationTypeValue.
 */
@RestController
@RequestMapping("/api")
public class AssetSpecificationTypeValueResource {

    private final Logger log = LoggerFactory.getLogger(AssetSpecificationTypeValueResource.class);

    @Inject
    private AssetSpecificationTypeValueService assetSpecificationTypeValueService;

    /**
     * POST  /asset-specification-type-values : Create a new assetSpecificationTypeValue.
     *
     * @param assetSpecificationTypeValueDTO the assetSpecificationTypeValueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assetSpecificationTypeValueDTO, or with status 400 (Bad Request) if the assetSpecificationTypeValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/asset-specification-type-values")
    @Timed
    public ResponseEntity<AssetSpecificationTypeValueDTO> createAssetSpecificationTypeValue(@Valid @RequestBody AssetSpecificationTypeValueDTO assetSpecificationTypeValueDTO) throws URISyntaxException {
        log.debug("REST request to save AssetSpecificationTypeValue : {}", assetSpecificationTypeValueDTO);
        if (assetSpecificationTypeValueDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("assetSpecificationTypeValue", "idexists", "A new assetSpecificationTypeValue cannot already have an ID")).body(null);
        }
        AssetSpecificationTypeValueDTO result = assetSpecificationTypeValueService.save(assetSpecificationTypeValueDTO);
        return ResponseEntity.created(new URI("/api/asset-specification-type-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetSpecificationTypeValue", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /asset-specification-type-values : Updates an existing assetSpecificationTypeValue.
     *
     * @param assetSpecificationTypeValueDTO the assetSpecificationTypeValueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assetSpecificationTypeValueDTO,
     * or with status 400 (Bad Request) if the assetSpecificationTypeValueDTO is not valid,
     * or with status 500 (Internal Server Error) if the assetSpecificationTypeValueDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/asset-specification-type-values")
    @Timed
    public ResponseEntity<AssetSpecificationTypeValueDTO> updateAssetSpecificationTypeValue(@Valid @RequestBody AssetSpecificationTypeValueDTO assetSpecificationTypeValueDTO) throws URISyntaxException {
        log.debug("REST request to update AssetSpecificationTypeValue : {}", assetSpecificationTypeValueDTO);
        if (assetSpecificationTypeValueDTO.getId() == null) {
            return createAssetSpecificationTypeValue(assetSpecificationTypeValueDTO);
        }
        AssetSpecificationTypeValueDTO result = assetSpecificationTypeValueService.save(assetSpecificationTypeValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetSpecificationTypeValue", assetSpecificationTypeValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /asset-specification-type-values : get all the assetSpecificationTypeData.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of assetSpecificationTypeData in body
     */
    @GetMapping("/asset-specification-type-values/{id}")
    @Timed
    public List<AssetSpecificationTypeValueDTO> getAllAssetSpecificationTypeValues(@PathVariable Long id) {
        log.debug("REST request to get all AssetSpecificationTypeValues");
        return assetSpecificationTypeValueService.findAllByAssetId(id);
    }

    /**
     * GET  /asset-specification-type-values/:id : get the "id" assetSpecificationTypeValue.
     *
     * @param id the id of the assetSpecificationTypeValueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assetSpecificationTypeValueDTO, or with status 404 (Not Found)
     */
//    @GetMapping("/asset-specification-type-values/{id}")
//    @Timed
//    public ResponseEntity<AssetSpecificationTypeValueDTO> getAssetSpecificationTypeValue(@PathVariable Long id) {
//        log.debug("REST request to get AssetSpecificationTypeValue : {}", id);
//        AssetSpecificationTypeValueDTO assetSpecificationTypeValueDTO = assetSpecificationTypeValueService.findAllByAssetId(id);
//        return Optional.ofNullable(assetSpecificationTypeValueDTO)
//            .map(result -> new ResponseEntity<>(
//                result,
//                HttpStatus.OK))
//            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    /**
     * DELETE  /asset-specification-type-values/:id : delete the "id" assetSpecificationTypeValue.
     *
     * @param id the id of the assetSpecificationTypeValueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/asset-specification-type-values/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssetSpecificationTypeValue(@PathVariable Long id) {
        log.debug("REST request to delete AssetSpecificationTypeValue : {}", id);
        assetSpecificationTypeValueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetSpecificationTypeValue", id.toString())).build();
    }

}
