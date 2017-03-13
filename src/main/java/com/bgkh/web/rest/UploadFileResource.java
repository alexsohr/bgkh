package com.bgkh.web.rest;

import com.bgkh.config.JHipsterProperties;
import com.bgkh.domain.UploadFile;
import com.codahale.metrics.annotation.Timed;
import com.bgkh.service.UploadFileService;
import com.bgkh.web.rest.util.HeaderUtil;
import com.bgkh.service.dto.UploadFileDTO;

import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
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
     * @param file file To upload
     * @return the ResponseEntity with status 201 (Created) and with body the new uploadFileDTO, or with status 400 (Bad Request) if the uploadFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/upload-files")
    @Timed
    public ResponseEntity<UploadFileDTO> createUploadFile(@RequestParam("file") MultipartFile file) throws URISyntaxException {

        String uploadPath = uploadFileService.getUploadPath();
//        String uploadPath = "C:\\bgkh\\data";

        UploadFileDTO uploadFileDTO = null;
        if (!file.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            try {
                byte[] bytes = file.getBytes();

                String newFileName = sdf.format(new Date())+"_" + file.getOriginalFilename();
                File newFile = new File(uploadPath, newFileName);
                BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(newFile));
                stream.write(bytes);
                stream.close();

                uploadFileDTO = new UploadFileDTO();
                uploadFileDTO.setLocation(newFile.getAbsolutePath());
                uploadFileDTO.setDeleted(false);
            } catch (Exception e) {
                log.error("You failed to upload " + file.getName() + " => " + e.getMessage());
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("uploadFile", "fileSaveError", "A new uploadFile cannot be saved")).body(null);
            }
        } else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("uploadFile", "idexists", "A new uploadFile cannot already have an ID")).body(null);
        }

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
    @GetMapping("/upload-files/download/{id}")
    @Timed
    public void downloadFile(@PathVariable Long id, HttpServletResponse response) {

        UploadFileDTO uploadFile = uploadFileService.findOne(id);
        File file = new File(uploadFile.getLocation());
        // Generate the http headers with the file properties
        response.addHeader("content-disposition", "attachment; filename=" + file.getName());
        String mime = URLConnection.guessContentTypeFromName(file.getName());
        response.setContentType(mime);
        try {
            Files.copy(file, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            log.error("unable to get file: {} with id: {}", file.getName(), id);
        }


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
