package com.bgkh.web.rest;

import com.bgkh.AppApp;

import com.bgkh.domain.AssetSpecificationTypeValue;
import com.bgkh.repository.AssetSpecificationTypeValueRepository;
import com.bgkh.service.AssetSpecificationTypeValueService;
import com.bgkh.service.dto.AssetSpecificationTypeValueDTO;
import com.bgkh.service.mapper.AssetSpecificationTypeValueMapper;

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
 * Test class for the AssetSpecificationTypeValueResource REST controller.
 *
 * @see AssetSpecificationTypeValueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class AssetSpecificationTypeValueResourceIntTest {

    private static final String DEFAULT_FIELD_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_VALUE = "BBBBBBBBBB";

    @Inject
    private AssetSpecificationTypeValueRepository assetSpecificationTypeValueRepository;

    @Inject
    private AssetSpecificationTypeValueMapper assetSpecificationTypeValueMapper;

    @Inject
    private AssetSpecificationTypeValueService assetSpecificationTypeValueService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAssetSpecificationTypeValueMockMvc;

    private AssetSpecificationTypeValue assetSpecificationTypeValue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetSpecificationTypeValueResource assetSpecificationTypeValueResource = new AssetSpecificationTypeValueResource();
        ReflectionTestUtils.setField(assetSpecificationTypeValueResource, "assetSpecificationTypeValueService", assetSpecificationTypeValueService);
        this.restAssetSpecificationTypeValueMockMvc = MockMvcBuilders.standaloneSetup(assetSpecificationTypeValueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetSpecificationTypeValue createEntity(EntityManager em) {
        AssetSpecificationTypeValue assetSpecificationTypeValue = new AssetSpecificationTypeValue()
                .fieldValue(DEFAULT_FIELD_VALUE);
        return assetSpecificationTypeValue;
    }

    @Before
    public void initTest() {
        assetSpecificationTypeValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssetSpecificationTypeValue() throws Exception {
        int databaseSizeBeforeCreate = assetSpecificationTypeValueRepository.findAll().size();

        // Create the AssetSpecificationTypeValue
        AssetSpecificationTypeValueDTO assetSpecificationTypeValueDTO = assetSpecificationTypeValueMapper.assetSpecificationTypeValueToAssetSpecificationTypeValueDTO(assetSpecificationTypeValue);

        restAssetSpecificationTypeValueMockMvc.perform(post("/api/asset-specification-type-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSpecificationTypeValueDTO)))
            .andExpect(status().isCreated());

        // Validate the AssetSpecificationTypeValue in the database
        List<AssetSpecificationTypeValue> assetSpecificationTypeValueList = assetSpecificationTypeValueRepository.findAll();
        assertThat(assetSpecificationTypeValueList).hasSize(databaseSizeBeforeCreate + 1);
        AssetSpecificationTypeValue testAssetSpecificationTypeValue = assetSpecificationTypeValueList.get(assetSpecificationTypeValueList.size() - 1);
        assertThat(testAssetSpecificationTypeValue.getFieldValue()).isEqualTo(DEFAULT_FIELD_VALUE);
    }

    @Test
    @Transactional
    public void createAssetSpecificationTypeValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetSpecificationTypeValueRepository.findAll().size();

        // Create the AssetSpecificationTypeValue with an existing ID
        AssetSpecificationTypeValue existingAssetSpecificationTypeValue = new AssetSpecificationTypeValue();
        existingAssetSpecificationTypeValue.setId(1L);
        AssetSpecificationTypeValueDTO existingAssetSpecificationTypeValueDTO = assetSpecificationTypeValueMapper.assetSpecificationTypeValueToAssetSpecificationTypeValueDTO(existingAssetSpecificationTypeValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetSpecificationTypeValueMockMvc.perform(post("/api/asset-specification-type-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAssetSpecificationTypeValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AssetSpecificationTypeValue> assetSpecificationTypeValueList = assetSpecificationTypeValueRepository.findAll();
        assertThat(assetSpecificationTypeValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFieldValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetSpecificationTypeValueRepository.findAll().size();
        // set the field null
        assetSpecificationTypeValue.setFieldValue(null);

        // Create the AssetSpecificationTypeValue, which fails.
        AssetSpecificationTypeValueDTO assetSpecificationTypeValueDTO = assetSpecificationTypeValueMapper.assetSpecificationTypeValueToAssetSpecificationTypeValueDTO(assetSpecificationTypeValue);

        restAssetSpecificationTypeValueMockMvc.perform(post("/api/asset-specification-type-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSpecificationTypeValueDTO)))
            .andExpect(status().isBadRequest());

        List<AssetSpecificationTypeValue> assetSpecificationTypeValueList = assetSpecificationTypeValueRepository.findAll();
        assertThat(assetSpecificationTypeValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetSpecificationTypeValues() throws Exception {
        // Initialize the database
        assetSpecificationTypeValueRepository.saveAndFlush(assetSpecificationTypeValue);

        // Get all the assetSpecificationTypeValueList
        restAssetSpecificationTypeValueMockMvc.perform(get("/api/asset-specification-type-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetSpecificationTypeValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldValue").value(hasItem(DEFAULT_FIELD_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getAssetSpecificationTypeValue() throws Exception {
        // Initialize the database
        assetSpecificationTypeValueRepository.saveAndFlush(assetSpecificationTypeValue);

        // Get the assetSpecificationTypeValue
        restAssetSpecificationTypeValueMockMvc.perform(get("/api/asset-specification-type-values/{id}", assetSpecificationTypeValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assetSpecificationTypeValue.getId().intValue()))
            .andExpect(jsonPath("$.fieldValue").value(DEFAULT_FIELD_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetSpecificationTypeValue() throws Exception {
        // Get the assetSpecificationTypeValue
        restAssetSpecificationTypeValueMockMvc.perform(get("/api/asset-specification-type-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetSpecificationTypeValue() throws Exception {
        // Initialize the database
        assetSpecificationTypeValueRepository.saveAndFlush(assetSpecificationTypeValue);
        int databaseSizeBeforeUpdate = assetSpecificationTypeValueRepository.findAll().size();

        // Update the assetSpecificationTypeValue
        AssetSpecificationTypeValue updatedAssetSpecificationTypeValue = assetSpecificationTypeValueRepository.findOne(assetSpecificationTypeValue.getId());
        updatedAssetSpecificationTypeValue
                .fieldValue(UPDATED_FIELD_VALUE);
        AssetSpecificationTypeValueDTO assetSpecificationTypeValueDTO = assetSpecificationTypeValueMapper.assetSpecificationTypeValueToAssetSpecificationTypeValueDTO(updatedAssetSpecificationTypeValue);

        restAssetSpecificationTypeValueMockMvc.perform(put("/api/asset-specification-type-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSpecificationTypeValueDTO)))
            .andExpect(status().isOk());

        // Validate the AssetSpecificationTypeValue in the database
        List<AssetSpecificationTypeValue> assetSpecificationTypeValueList = assetSpecificationTypeValueRepository.findAll();
        assertThat(assetSpecificationTypeValueList).hasSize(databaseSizeBeforeUpdate);
        AssetSpecificationTypeValue testAssetSpecificationTypeValue = assetSpecificationTypeValueList.get(assetSpecificationTypeValueList.size() - 1);
        assertThat(testAssetSpecificationTypeValue.getFieldValue()).isEqualTo(UPDATED_FIELD_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingAssetSpecificationTypeValue() throws Exception {
        int databaseSizeBeforeUpdate = assetSpecificationTypeValueRepository.findAll().size();

        // Create the AssetSpecificationTypeValue
        AssetSpecificationTypeValueDTO assetSpecificationTypeValueDTO = assetSpecificationTypeValueMapper.assetSpecificationTypeValueToAssetSpecificationTypeValueDTO(assetSpecificationTypeValue);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetSpecificationTypeValueMockMvc.perform(put("/api/asset-specification-type-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSpecificationTypeValueDTO)))
            .andExpect(status().isCreated());

        // Validate the AssetSpecificationTypeValue in the database
        List<AssetSpecificationTypeValue> assetSpecificationTypeValueList = assetSpecificationTypeValueRepository.findAll();
        assertThat(assetSpecificationTypeValueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssetSpecificationTypeValue() throws Exception {
        // Initialize the database
        assetSpecificationTypeValueRepository.saveAndFlush(assetSpecificationTypeValue);
        int databaseSizeBeforeDelete = assetSpecificationTypeValueRepository.findAll().size();

        // Get the assetSpecificationTypeValue
        restAssetSpecificationTypeValueMockMvc.perform(delete("/api/asset-specification-type-values/{id}", assetSpecificationTypeValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetSpecificationTypeValue> assetSpecificationTypeValueList = assetSpecificationTypeValueRepository.findAll();
        assertThat(assetSpecificationTypeValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
