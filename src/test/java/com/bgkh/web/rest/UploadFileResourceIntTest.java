package com.bgkh.web.rest;

import com.bgkh.AppApp;

import com.bgkh.domain.UploadFile;
import com.bgkh.repository.UploadFileRepository;
import com.bgkh.service.UploadFileService;
import com.bgkh.service.dto.UploadFileDTO;
import com.bgkh.service.mapper.UploadFileMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UploadFileResource REST controller.
 *
 * @see UploadFileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class UploadFileResourceIntTest {

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Inject
    private UploadFileRepository uploadFileRepository;

    @Inject
    private UploadFileMapper uploadFileMapper;

    @Inject
    private UploadFileService uploadFileService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUploadFileMockMvc;

    private UploadFile uploadFile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UploadFileResource uploadFileResource = new UploadFileResource();
        ReflectionTestUtils.setField(uploadFileResource, "uploadFileService", uploadFileService);
        this.restUploadFileMockMvc = MockMvcBuilders.standaloneSetup(uploadFileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UploadFile createEntity(EntityManager em) {
        UploadFile uploadFile = new UploadFile()
                .location(DEFAULT_LOCATION)
                .deleted(DEFAULT_DELETED);
        return uploadFile;
    }

    @Before
    public void initTest() {
        uploadFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadFile() throws Exception {
        int databaseSizeBeforeCreate = uploadFileRepository.findAll().size();

        // Create the UploadFile
        UploadFileDTO uploadFileDTO = uploadFileMapper.uploadFileToUploadFileDTO(uploadFile);

        restUploadFileMockMvc.perform(post("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadFileDTO)))
            .andExpect(status().isCreated());

        // Validate the UploadFile in the database
        List<UploadFile> uploadFileList = uploadFileRepository.findAll();
        assertThat(uploadFileList).hasSize(databaseSizeBeforeCreate + 1);
        UploadFile testUploadFile = uploadFileList.get(uploadFileList.size() - 1);
        assertThat(testUploadFile.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testUploadFile.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createUploadFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadFileRepository.findAll().size();

        // Create the UploadFile with an existing ID
        UploadFile existingUploadFile = new UploadFile();
        existingUploadFile.setId(1L);
        UploadFileDTO existingUploadFileDTO = uploadFileMapper.uploadFileToUploadFileDTO(existingUploadFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadFileMockMvc.perform(post("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUploadFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UploadFile> uploadFileList = uploadFileRepository.findAll();
        assertThat(uploadFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadFileRepository.findAll().size();
        // set the field null
        uploadFile.setLocation(null);

        // Create the UploadFile, which fails.
        UploadFileDTO uploadFileDTO = uploadFileMapper.uploadFileToUploadFileDTO(uploadFile);

        restUploadFileMockMvc.perform(post("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadFileDTO)))
            .andExpect(status().isBadRequest());

        List<UploadFile> uploadFileList = uploadFileRepository.findAll();
        assertThat(uploadFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadFileRepository.findAll().size();
        // set the field null
        uploadFile.setDeleted(null);

        // Create the UploadFile, which fails.
        UploadFileDTO uploadFileDTO = uploadFileMapper.uploadFileToUploadFileDTO(uploadFile);

        restUploadFileMockMvc.perform(post("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadFileDTO)))
            .andExpect(status().isBadRequest());

        List<UploadFile> uploadFileList = uploadFileRepository.findAll();
        assertThat(uploadFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUploadFiles() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList
        restUploadFileMockMvc.perform(get("/api/upload-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getUploadFile() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get the uploadFile
        restUploadFileMockMvc.perform(get("/api/upload-files/{id}", uploadFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uploadFile.getId().intValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUploadFile() throws Exception {
        // Get the uploadFile
        restUploadFileMockMvc.perform(get("/api/upload-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadFile() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);
        int databaseSizeBeforeUpdate = uploadFileRepository.findAll().size();

        // Update the uploadFile
        UploadFile updatedUploadFile = uploadFileRepository.findOne(uploadFile.getId());
        updatedUploadFile
                .location(UPDATED_LOCATION)
                .deleted(UPDATED_DELETED);
        UploadFileDTO uploadFileDTO = uploadFileMapper.uploadFileToUploadFileDTO(updatedUploadFile);

        restUploadFileMockMvc.perform(put("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadFileDTO)))
            .andExpect(status().isOk());

        // Validate the UploadFile in the database
        List<UploadFile> uploadFileList = uploadFileRepository.findAll();
        assertThat(uploadFileList).hasSize(databaseSizeBeforeUpdate);
        UploadFile testUploadFile = uploadFileList.get(uploadFileList.size() - 1);
        assertThat(testUploadFile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testUploadFile.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = uploadFileRepository.findAll().size();

        // Create the UploadFile
        UploadFileDTO uploadFileDTO = uploadFileMapper.uploadFileToUploadFileDTO(uploadFile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUploadFileMockMvc.perform(put("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadFileDTO)))
            .andExpect(status().isCreated());

        // Validate the UploadFile in the database
        List<UploadFile> uploadFileList = uploadFileRepository.findAll();
        assertThat(uploadFileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUploadFile() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);
        int databaseSizeBeforeDelete = uploadFileRepository.findAll().size();

        // Get the uploadFile
        restUploadFileMockMvc.perform(delete("/api/upload-files/{id}", uploadFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UploadFile> uploadFileList = uploadFileRepository.findAll();
        assertThat(uploadFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
