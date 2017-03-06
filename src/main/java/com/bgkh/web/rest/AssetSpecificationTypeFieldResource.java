package com.bgkh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bgkh.service.AssetSpecificationTypeFieldService;
import com.bgkh.web.rest.util.HeaderUtil;
import com.bgkh.service.dto.AssetSpecificationTypeFieldDTO;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing AssetSpecificationTypeField.
 */
@RestController
@RequestMapping("/api")
public class AssetSpecificationTypeFieldResource {

    private final Logger log = LoggerFactory.getLogger(AssetSpecificationTypeFieldResource.class);

    @Inject
    private AssetSpecificationTypeFieldService assetSpecificationTypeFieldService;

    /**
     * POST  /asset-specification-type-fields : Create a new assetSpecificationTypeField.
     *
     * @param assetSpecificationTypeFieldDTO the assetSpecificationTypeFieldDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assetSpecificationTypeFieldDTO, or with status 400 (Bad Request) if the assetSpecificationTypeField has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/asset-specification-type-fields")
    @Timed
    public ResponseEntity<AssetSpecificationTypeFieldDTO> createAssetSpecificationTypeField(@Valid @RequestBody AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO) throws URISyntaxException {
        log.debug("REST request to save AssetSpecificationTypeField : {}", assetSpecificationTypeFieldDTO);
        if (assetSpecificationTypeFieldDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("assetSpecificationTypeField", "idexists", "A new assetSpecificationTypeField cannot already have an ID")).body(null);
        }
        AssetSpecificationTypeFieldDTO result = assetSpecificationTypeFieldService.save(assetSpecificationTypeFieldDTO);
        return ResponseEntity.created(new URI("/api/asset-specification-type-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetSpecificationTypeField", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /asset-specification-type-fields : Updates an existing assetSpecificationTypeField.
     *
     * @param assetSpecificationTypeFieldDTO the assetSpecificationTypeFieldDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assetSpecificationTypeFieldDTO,
     * or with status 400 (Bad Request) if the assetSpecificationTypeFieldDTO is not valid,
     * or with status 500 (Internal Server Error) if the assetSpecificationTypeFieldDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/asset-specification-type-fields")
    @Timed
    public ResponseEntity<AssetSpecificationTypeFieldDTO> updateAssetSpecificationTypeField(@Valid @RequestBody AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO) throws URISyntaxException {
        log.debug("REST request to update AssetSpecificationTypeField : {}", assetSpecificationTypeFieldDTO);
        if (assetSpecificationTypeFieldDTO.getId() == null) {
            return createAssetSpecificationTypeField(assetSpecificationTypeFieldDTO);
        }
        AssetSpecificationTypeFieldDTO result = assetSpecificationTypeFieldService.save(assetSpecificationTypeFieldDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetSpecificationTypeField", assetSpecificationTypeFieldDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /asset-specification-type-fields : get all the assetSpecificationTypeFields.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of assetSpecificationTypeFields in body
     */
    @GetMapping("/asset-specification-type-fields/byType/{assetSpecificationTypeId}")
    @Timed
    public List<AssetSpecificationTypeFieldDTO> getAllAssetSpecificationTypeFields(@PathVariable Long assetSpecificationTypeId) {
        log.debug("REST request to get all AssetSpecificationTypeFields");
        return assetSpecificationTypeFieldService.findAllByAssetSpecificationTypeId(assetSpecificationTypeId);
    }

    /**
     * GET  /asset-specification-type-fields/:id : get the "id" assetSpecificationTypeField.
     *
     * @param id the id of the assetSpecificationTypeFieldDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assetSpecificationTypeFieldDTO, or with status 404 (Not Found)
     */
    @GetMapping("/asset-specification-type-fields/{id}")
    @Timed
    public ResponseEntity<AssetSpecificationTypeFieldDTO> getAssetSpecificationTypeField(@PathVariable Long id) {
        log.debug("REST request to get AssetSpecificationTypeField : {}", id);
        AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO = assetSpecificationTypeFieldService.findOne(id);
        return Optional.ofNullable(assetSpecificationTypeFieldDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /asset-specification-type-fields/:id : delete the "id" assetSpecificationTypeField.
     *
     * @param id the id of the assetSpecificationTypeFieldDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/asset-specification-type-fields/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssetSpecificationTypeField(@PathVariable Long id) {
        log.debug("REST request to delete AssetSpecificationTypeField : {}", id);
        assetSpecificationTypeFieldService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetSpecificationTypeField", id.toString())).build();
    }

}
