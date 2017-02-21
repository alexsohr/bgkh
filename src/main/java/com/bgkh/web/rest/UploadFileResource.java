package com.bgkh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bgkh.service.UploadFileService;
import com.bgkh.web.rest.util.HeaderUtil;
import com.bgkh.service.dto.UploadFileDTO;

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
 * REST controller for managing UploadFile.
 */
@RestController
@RequestMapping("/api")
public class UploadFileResource {

    private final Logger log = LoggerFactory.getLogger(UploadFileResource.class);
        
    @Inject
    private UploadFileService uploadFileService;

    /**
     * POST  /upload-files : Create a new uploadFile.
     *
     * @param uploadFileDTO the uploadFileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uploadFileDTO, or with status 400 (Bad Request) if the uploadFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/upload-files")
    @Timed
    public ResponseEntity<UploadFileDTO> createUploadFile(@Valid @RequestBody UploadFileDTO uploadFileDTO) throws URISyntaxException {
        log.debug("REST request to save UploadFile : {}", uploadFileDTO);
        if (uploadFileDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("uploadFile", "idexists", "A new uploadFile cannot already have an ID")).body(null);
        }
        UploadFileDTO result = uploadFileService.save(uploadFileDTO);
        return ResponseEntity.created(new URI("/api/upload-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("uploadFile", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /upload-files : Updates an existing uploadFile.
     *
     * @param uploadFileDTO the uploadFileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uploadFileDTO,
     * or with status 400 (Bad Request) if the uploadFileDTO is not valid,
     * or with status 500 (Internal Server Error) if the uploadFileDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/upload-files")
    @Timed
    public ResponseEntity<UploadFileDTO> updateUploadFile(@Valid @RequestBody UploadFileDTO uploadFileDTO) throws URISyntaxException {
        log.debug("REST request to update UploadFile : {}", uploadFileDTO);
        if (uploadFileDTO.getId() == null) {
            return createUploadFile(uploadFileDTO);
        }
        UploadFileDTO result = uploadFileService.save(uploadFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("uploadFile", uploadFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /upload-files : get all the uploadFiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of uploadFiles in body
     */
    @GetMapping("/upload-files")
    @Timed
    public List<UploadFileDTO> getAllUploadFiles() {
        log.debug("REST request to get all UploadFiles");
        return uploadFileService.findAll();
    }

    /**
     * GET  /upload-files/:id : get the "id" uploadFile.
     *
     * @param id the id of the uploadFileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uploadFileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/upload-files/{id}")
    @Timed
    public ResponseEntity<UploadFileDTO> getUploadFile(@PathVariable Long id) {
        log.debug("REST request to get UploadFile : {}", id);
        UploadFileDTO uploadFileDTO = uploadFileService.findOne(id);
        return Optional.ofNullable(uploadFileDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /upload-files/:id : delete the "id" uploadFile.
     *
     * @param id the id of the uploadFileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/upload-files/{id}")
    @Timed
    public ResponseEntity<Void> deleteUploadFile(@PathVariable Long id) {
        log.debug("REST request to delete UploadFile : {}", id);
        uploadFileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("uploadFile", id.toString())).build();
    }

}
