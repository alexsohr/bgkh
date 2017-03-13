package com.bgkh.service.impl;

import com.bgkh.service.UploadFileService;
import com.bgkh.domain.UploadFile;
import com.bgkh.repository.UploadFileRepository;
import com.bgkh.service.dto.UploadFileDTO;
import com.bgkh.service.mapper.UploadFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UploadFile.
 */
@Service
@Transactional
public class UploadFileServiceImpl implements UploadFileService{

    private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
//    private final String uploadPath = "/home/alex/Desktop/test";
    private final String uploadPath = System.getenv("BGKH_UPLOAD_PATH");

    @Inject
    private UploadFileRepository uploadFileRepository;

    @Inject
    private UploadFileMapper uploadFileMapper;

    /**
     * Save a uploadFile.
     *
     * @param uploadFileDTO the entity to save
     * @return the persisted entity
     */
    public UploadFileDTO save(UploadFileDTO uploadFileDTO) {
        log.debug("Request to save UploadFile : {}", uploadFileDTO);
        UploadFile uploadFile = uploadFileMapper.uploadFileDTOToUploadFile(uploadFileDTO);
        uploadFile = uploadFileRepository.save(uploadFile);
        UploadFileDTO result = uploadFileMapper.uploadFileToUploadFileDTO(uploadFile);
        return result;
    }

    /**
     *  Get all the uploadFiles.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UploadFileDTO> findAll() {
        log.debug("Request to get all UploadFiles");
        List<UploadFileDTO> result = uploadFileRepository.findAll().stream()
            .map(uploadFileMapper::uploadFileToUploadFileDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one uploadFile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UploadFileDTO findOne(Long id) {
        log.debug("Request to get UploadFile : {}", id);
        UploadFile uploadFile = uploadFileRepository.findOne(id);
        UploadFileDTO uploadFileDTO = uploadFileMapper.uploadFileToUploadFileDTO(uploadFile);
        return uploadFileDTO;
    }

    /**
     *  Delete the  uploadFile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UploadFile : {}", id);
        UploadFile uploadFile = uploadFileRepository.findOne(id);
        try {
            File file = new File(uploadFile.getLocation());
            if (file.exists()) {
                file.delete();
            }
            uploadFileRepository.delete(id);
        }
        catch (Exception e) {
            // Do nothing
        }
    }

    public String getUploadPath() {
        return uploadPath;
    }
}
