package com.bgkh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bgkh.service.AssetService;
import com.bgkh.service.dto.AssetDTO;
import com.bgkh.service.dto.AssetDTOs;
import com.bgkh.service.mapper.AssetMapper;
import com.bgkh.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
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
 * REST controller for managing Asset.
 */
@RestController
@RequestMapping("/api")
public class AssetResource {

    private final Logger log = LoggerFactory.getLogger(AssetResource.class);

    @Inject
    private AssetService assetService;
    @Inject
    private AssetMapper assetMapper;

    /**
     * POST  /assets : Create a new asset.
     *
     * @param assetDTO the assetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assetDTO, or with status 400 (Bad Request) if the asset has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assets")
    @Timed
    public ResponseEntity<AssetDTO> createAsset(@Valid @RequestBody AssetDTO assetDTO) throws URISyntaxException {
        log.debug("REST request to save Asset : {}", assetDTO);
        if (assetDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("asset", "idexists", "A new asset cannot already have an ID")).body(null);
        }
        AssetDTO result = assetService.save(assetDTO);
        return ResponseEntity.created(new URI("/api/assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("asset", result.getId().toString()))
            .body(result);
    }

    @PostMapping("/assets/copy")
    @Timed
    public ResponseEntity<AssetDTOs> copyAsset(@Valid @RequestBody AssetDTOs assetDTOs) throws URISyntaxException {
        log.debug("REST request to copy Assets : {}", assetDTOs);

        AssetDTOs result = assetService.copy(assetDTOs);
        return ResponseEntity.created(new URI("/api/assets/"))
            .headers(HeaderUtil.createEntityCreationAlert("asset", result.getAssetList().get(0).getId().toString()))
            .body(result);
    }

    @PostMapping("/assets/move/{id}")
    @Timed
    public ResponseEntity<AssetDTO> moveAsset(@Valid @RequestBody Long parentId, @PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to move Asset with id : {}", id);

        AssetDTO assetDTO = assetService.findOne(id);

        AssetDTO result = assetService.save(assetDTO);
        return ResponseEntity.created(new URI("/api/assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("asset", result.getId().toString()))
            .body(result);
    }

    @PostMapping("/assets/all")
    @Timed
    public ResponseEntity<AssetDTOs> createAllAssets(@Valid @RequestBody AssetDTOs assetDTOs) throws URISyntaxException {
        log.debug("REST request to save Assets : {}", assetDTOs);

        AssetDTOs result = assetService.saveAll(assetDTOs);
        return ResponseEntity.created(new URI("/api/assets/" + result.getAssetList().get(0).getId()))
            .headers(HeaderUtil.createEntityCreationAlert("asset", result.getAssetList().get(0).getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assets : Updates an existing asset.
     *
     * @param assetDTO the assetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assetDTO,
     * or with status 400 (Bad Request) if the assetDTO is not valid,
     * or with status 500 (Internal Server Error) if the assetDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assets")
    @Timed
    public ResponseEntity<AssetDTO> updateAsset(@Valid @RequestBody AssetDTO assetDTO) throws URISyntaxException {
        log.debug("REST request to update Asset : {}", assetDTO);
        if (assetDTO.getId() == null) {
            return createAsset(assetDTO);
        }
        AssetDTO result = assetService.save(assetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("asset", assetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assets : get all the assets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of assets in body
     */
    @GetMapping("/assets")
    @Timed
    public List<AssetDTO> getAllAssets() {
        log.debug("REST request to get all Assets");
        return assetService.findAll();
    }

    @GetMapping("/assets/manufactures")
    @Timed
    public List<String> getAllManufactures() {
        log.debug("REST request to get all Assets Manufactures");
        return assetService.findAllManufactures();
    }

    @GetMapping("/assets/names")
    @Timed
    public List<String> getAllAssetNames() {
        log.debug("REST request to get all Asset names");
        return assetService.findAllAssetNames();
    }

    @GetMapping("/assets/locations")
    @Timed
    public List<String> getAllAssetLocations() {
        log.debug("REST request to get all Asset locations");
        return assetService.findAllAssetLocations();
    }

    /**
     * GET  /assets/:id : get the "id" asset.
     *
     * @param id the id of the assetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/assets/{id}")
    @Timed
    public ResponseEntity<AssetDTO> getAsset(@PathVariable Long id) {
        log.debug("REST request to get Asset : {}", id);
        AssetDTO assetDTO = assetService.findOne(id);
        return Optional.ofNullable(assetDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assets/:id : delete the "id" asset.
     *
     * @param id the id of the assetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assets/{id}")
    @Timed
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        log.debug("REST request to delete Asset : {}", id);
        assetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("asset", id.toString())).build();
    }

}
