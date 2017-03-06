package com.bgkh.web.rest;

import com.bgkh.AppApp;

import com.bgkh.domain.AssetSpecificationTypeField;
import com.bgkh.repository.AssetSpecificationTypeFieldRepository;
import com.bgkh.service.AssetSpecificationTypeFieldService;
import com.bgkh.service.dto.AssetSpecificationTypeFieldDTO;
import com.bgkh.service.mapper.AssetSpecificationTypeFieldMapper;

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
 * Test class for the AssetSpecificationTypeFieldResource REST controller.
 *
 * @see AssetSpecificationTypeFieldResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class AssetSpecificationTypeFieldResourceIntTest {

    private static final String DEFAULT_FIELD_LABLE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_LABLE = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_TYPE = "BBBBBBBBBB";

    @Inject
    private AssetSpecificationTypeFieldRepository assetSpecificationTypeFieldRepository;

    @Inject
    private AssetSpecificationTypeFieldMapper assetSpecificationTypeFieldMapper;

    @Inject
    private AssetSpecificationTypeFieldService assetSpecificationTypeFieldService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAssetSpecificationTypeFieldMockMvc;

    private AssetSpecificationTypeField assetSpecificationTypeField;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetSpecificationTypeFieldResource assetSpecificationTypeFieldResource = new AssetSpecificationTypeFieldResource();
        ReflectionTestUtils.setField(assetSpecificationTypeFieldResource, "assetSpecificationTypeFieldService", assetSpecificationTypeFieldService);
        this.restAssetSpecificationTypeFieldMockMvc = MockMvcBuilders.standaloneSetup(assetSpecificationTypeFieldResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetSpecificationTypeField createEntity(EntityManager em) {
        AssetSpecificationTypeField assetSpecificationTypeField = new AssetSpecificationTypeField()
                .fieldLable(DEFAULT_FIELD_LABLE)
                .fieldName(DEFAULT_FIELD_NAME)
                .fieldType(DEFAULT_FIELD_TYPE);
        return assetSpecificationTypeField;
    }

    @Before
    public void initTest() {
        assetSpecificationTypeField = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssetSpecificationTypeField() throws Exception {
        int databaseSizeBeforeCreate = assetSpecificationTypeFieldRepository.findAll().size();

        // Create the AssetSpecificationTypeField
        AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO = assetSpecificationTypeFieldMapper.assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO(assetSpecificationTypeField);

        restAssetSpecificationTypeFieldMockMvc.perform(post("/api/asset-specification-type-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSpecificationTypeFieldDTO)))
            .andExpect(status().isCreated());

        // Validate the AssetSpecificationTypeField in the database
        List<AssetSpecificationTypeField> assetSpecificationTypeFieldList = assetSpecificationTypeFieldRepository.findAll();
        assertThat(assetSpecificationTypeFieldList).hasSize(databaseSizeBeforeCreate + 1);
        AssetSpecificationTypeField testAssetSpecificationTypeField = assetSpecificationTypeFieldList.get(assetSpecificationTypeFieldList.size() - 1);
        assertThat(testAssetSpecificationTypeField.getFieldLable()).isEqualTo(DEFAULT_FIELD_LABLE);
        assertThat(testAssetSpecificationTypeField.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testAssetSpecificationTypeField.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
    }

    @Test
    @Transactional
    public void createAssetSpecificationTypeFieldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetSpecificationTypeFieldRepository.findAll().size();

        // Create the AssetSpecificationTypeField with an existing ID
        AssetSpecificationTypeField existingAssetSpecificationTypeField = new AssetSpecificationTypeField();
        existingAssetSpecificationTypeField.setId(1L);
        AssetSpecificationTypeFieldDTO existingAssetSpecificationTypeFieldDTO = assetSpecificationTypeFieldMapper.assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO(existingAssetSpecificationTypeField);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetSpecificationTypeFieldMockMvc.perform(post("/api/asset-specification-type-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAssetSpecificationTypeFieldDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AssetSpecificationTypeField> assetSpecificationTypeFieldList = assetSpecificationTypeFieldRepository.findAll();
        assertThat(assetSpecificationTypeFieldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFieldLableIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetSpecificationTypeFieldRepository.findAll().size();
        // set the field null
        assetSpecificationTypeField.setFieldLable(null);

        // Create the AssetSpecificationTypeField, which fails.
        AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO = assetSpecificationTypeFieldMapper.assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO(assetSpecificationTypeField);

        restAssetSpecificationTypeFieldMockMvc.perform(post("/api/asset-specification-type-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSpecificationTypeFieldDTO)))
            .andExpect(status().isBadRequest());

        List<AssetSpecificationTypeField> assetSpecificationTypeFieldList = assetSpecificationTypeFieldRepository.findAll();
        assertThat(assetSpecificationTypeFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetSpecificationTypeFieldRepository.findAll().size();
        // set the field null
        assetSpecificationTypeField.setFieldName(null);

        // Create the AssetSpecificationTypeField, which fails.
        AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO = assetSpecificationTypeFieldMapper.assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO(assetSpecificationTypeField);

        restAssetSpecificationTypeFieldMockMvc.perform(post("/api/asset-specification-type-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSpecificationTypeFieldDTO)))
            .andExpect(status().isBadRequest());

        List<AssetSpecificationTypeField> assetSpecificationTypeFieldList = assetSpecificationTypeFieldRepository.findAll();
        assertThat(assetSpecificationTypeFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetSpecificationTypeFieldRepository.findAll().size();
        // set the field null
        assetSpecificationTypeField.setFieldType(null);

        // Create the AssetSpecificationTypeField, which fails.
        AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO = assetSpecificationTypeFieldMapper.assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO(assetSpecificationTypeField);

        restAssetSpecificationTypeFieldMockMvc.perform(post("/api/asset-specification-type-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSpecificationTypeFieldDTO)))
            .andExpect(status().isBadRequest());

        List<AssetSpecificationTypeField> assetSpecificationTypeFieldList = assetSpecificationTypeFieldRepository.findAll();
        assertThat(assetSpecificationTypeFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetSpecificationTypeFields() throws Exception {
        // Initialize the database
        assetSpecificationTypeFieldRepository.saveAndFlush(assetSpecificationTypeField);

        // Get all the assetSpecificationTypeFieldList
        restAssetSpecificationTypeFieldMockMvc.perform(get("/api/asset-specification-type-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetSpecificationTypeField.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldLable").value(hasItem(DEFAULT_FIELD_LABLE.toString())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME.toString())))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getAssetSpecificationTypeField() throws Exception {
        // Initialize the database
        assetSpecificationTypeFieldRepository.saveAndFlush(assetSpecificationTypeField);

        // Get the assetSpecificationTypeField
        restAssetSpecificationTypeFieldMockMvc.perform(get("/api/asset-specification-type-fields/{id}", assetSpecificationTypeField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assetSpecificationTypeField.getId().intValue()))
            .andExpect(jsonPath("$.fieldLable").value(DEFAULT_FIELD_LABLE.toString()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME.toString()))
            .andExpect(jsonPath("$.fieldType").value(DEFAULT_FIELD_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetSpecificationTypeField() throws Exception {
        // Get the assetSpecificationTypeField
        restAssetSpecificationTypeFieldMockMvc.perform(get("/api/asset-specification-type-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetSpecificationTypeField() throws Exception {
        // Initialize the database
        assetSpecificationTypeFieldRepository.saveAndFlush(assetSpecificationTypeField);
        int databaseSizeBeforeUpdate = assetSpecificationTypeFieldRepository.findAll().size();

        // Update the assetSpecificationTypeField
        AssetSpecificationTypeField updatedAssetSpecificationTypeField = assetSpecificationTypeFieldRepository.findOne(assetSpecificationTypeField.getId());
        updatedAssetSpecificationTypeField
                .fieldLable(UPDATED_FIELD_LABLE)
                .fieldName(UPDATED_FIELD_NAME)
                .fieldType(UPDATED_FIELD_TYPE);
        AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO = assetSpecificationTypeFieldMapper.assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO(updatedAssetSpecificationTypeField);

        restAssetSpecificationTypeFieldMockMvc.perform(put("/api/asset-specification-type-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSpecificationTypeFieldDTO)))
            .andExpect(status().isOk());

        // Validate the AssetSpecificationTypeField in the database
        List<AssetSpecificationTypeField> assetSpecificationTypeFieldList = assetSpecificationTypeFieldRepository.findAll();
        assertThat(assetSpecificationTypeFieldList).hasSize(databaseSizeBeforeUpdate);
        AssetSpecificationTypeField testAssetSpecificationTypeField = assetSpecificationTypeFieldList.get(assetSpecificationTypeFieldList.size() - 1);
        assertThat(testAssetSpecificationTypeField.getFieldLable()).isEqualTo(UPDATED_FIELD_LABLE);
        assertThat(testAssetSpecificationTypeField.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testAssetSpecificationTypeField.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAssetSpecificationTypeField() throws Exception {
        int databaseSizeBeforeUpdate = assetSpecificationTypeFieldRepository.findAll().size();

        // Create the AssetSpecificationTypeField
        AssetSpecificationTypeFieldDTO assetSpecificationTypeFieldDTO = assetSpecificationTypeFieldMapper.assetSpecificationTypeFieldToAssetSpecificationTypeFieldDTO(assetSpecificationTypeField);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetSpecificationTypeFieldMockMvc.perform(put("/api/asset-specification-type-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSpecificationTypeFieldDTO)))
            .andExpect(status().isCreated());

        // Validate the AssetSpecificationTypeField in the database
        List<AssetSpecificationTypeField> assetSpecificationTypeFieldList = assetSpecificationTypeFieldRepository.findAll();
        assertThat(assetSpecificationTypeFieldList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssetSpecificationTypeField() throws Exception {
        // Initialize the database
        assetSpecificationTypeFieldRepository.saveAndFlush(assetSpecificationTypeField);
        int databaseSizeBeforeDelete = assetSpecificationTypeFieldRepository.findAll().size();

        // Get the assetSpecificationTypeField
        restAssetSpecificationTypeFieldMockMvc.perform(delete("/api/asset-specification-type-fields/{id}", assetSpecificationTypeField.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetSpecificationTypeField> assetSpecificationTypeFieldList = assetSpecificationTypeFieldRepository.findAll();
        assertThat(assetSpecificationTypeFieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
