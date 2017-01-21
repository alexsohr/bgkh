package com.bgkh.web.rest;

import com.bgkh.AppApp;

import com.bgkh.domain.WorkOrder;
import com.bgkh.repository.WorkOrderRepository;
import com.bgkh.service.WorkOrderService;
import com.bgkh.service.dto.WorkOrderDTO;
import com.bgkh.service.mapper.WorkOrderMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bgkh.domain.enumeration.WorkOrderStatus;
import com.bgkh.domain.enumeration.WorkOrderType;
import com.bgkh.domain.enumeration.WorkOrderPriority;
import com.bgkh.domain.enumeration.WorkOrderEstStatus;
/**
 * Test class for the WorkOrderResource REST controller.
 *
 * @see WorkOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class WorkOrderResourceIntTest {

    private static final WorkOrderStatus DEFAULT_WORK_ORDER_STATUS = WorkOrderStatus.IN_SERVICE;
    private static final WorkOrderStatus UPDATED_WORK_ORDER_STATUS = WorkOrderStatus.OUT_OF_SERVICE;

    private static final WorkOrderType DEFAULT_WORK_ORDER_TYPE = WorkOrderType.STRATEGIC;
    private static final WorkOrderType UPDATED_WORK_ORDER_TYPE = WorkOrderType.NON_STRATEGIC;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_COMPLETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_COMPLETED = LocalDate.now(ZoneId.systemDefault());

    private static final WorkOrderPriority DEFAULT_PRIORITY = WorkOrderPriority.URGENT;
    private static final WorkOrderPriority UPDATED_PRIORITY = WorkOrderPriority.HIGH;

    private static final WorkOrderEstStatus DEFAULT_EST = WorkOrderEstStatus.NOT_STARTED;
    private static final WorkOrderEstStatus UPDATED_EST = WorkOrderEstStatus.IN_PROGRESS;

    private static final Long DEFAULT_ESTIMATED_HOURS = 1L;
    private static final Long UPDATED_ESTIMATED_HOURS = 2L;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TRACKER = false;
    private static final Boolean UPDATED_TRACKER = true;

    @Inject
    private WorkOrderRepository workOrderRepository;

    @Inject
    private WorkOrderMapper workOrderMapper;

    @Inject
    private WorkOrderService workOrderService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWorkOrderMockMvc;

    private WorkOrder workOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkOrderResource workOrderResource = new WorkOrderResource();
        ReflectionTestUtils.setField(workOrderResource, "workOrderService", workOrderService);
        this.restWorkOrderMockMvc = MockMvcBuilders.standaloneSetup(workOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkOrder createEntity(EntityManager em) {
        WorkOrder workOrder = new WorkOrder()
                .workOrderStatus(DEFAULT_WORK_ORDER_STATUS)
                .workOrderType(DEFAULT_WORK_ORDER_TYPE)
                .description(DEFAULT_DESCRIPTION)
                .dateCompleted(DEFAULT_DATE_COMPLETED)
                .priority(DEFAULT_PRIORITY)
                .est(DEFAULT_EST)
                .estimatedHours(DEFAULT_ESTIMATED_HOURS)
                .comments(DEFAULT_COMMENTS)
                .tracker(DEFAULT_TRACKER);
        return workOrder;
    }

    @Before
    public void initTest() {
        workOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkOrder() throws Exception {
        int databaseSizeBeforeCreate = workOrderRepository.findAll().size();

        // Create the WorkOrder
        WorkOrderDTO workOrderDTO = workOrderMapper.workOrderToWorkOrderDTO(workOrder);

        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkOrder in the database
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeCreate + 1);
        WorkOrder testWorkOrder = workOrderList.get(workOrderList.size() - 1);
        assertThat(testWorkOrder.getWorkOrderStatus()).isEqualTo(DEFAULT_WORK_ORDER_STATUS);
        assertThat(testWorkOrder.getWorkOrderType()).isEqualTo(DEFAULT_WORK_ORDER_TYPE);
        assertThat(testWorkOrder.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWorkOrder.getDateCompleted()).isEqualTo(DEFAULT_DATE_COMPLETED);
        assertThat(testWorkOrder.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testWorkOrder.getEst()).isEqualTo(DEFAULT_EST);
        assertThat(testWorkOrder.getEstimatedHours()).isEqualTo(DEFAULT_ESTIMATED_HOURS);
        assertThat(testWorkOrder.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testWorkOrder.isTracker()).isEqualTo(DEFAULT_TRACKER);
    }

    @Test
    @Transactional
    public void createWorkOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workOrderRepository.findAll().size();

        // Create the WorkOrder with an existing ID
        WorkOrder existingWorkOrder = new WorkOrder();
        existingWorkOrder.setId(1L);
        WorkOrderDTO existingWorkOrderDTO = workOrderMapper.workOrderToWorkOrderDTO(existingWorkOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWorkOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWorkOrderStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = workOrderRepository.findAll().size();
        // set the field null
        workOrder.setWorkOrderStatus(null);

        // Create the WorkOrder, which fails.
        WorkOrderDTO workOrderDTO = workOrderMapper.workOrderToWorkOrderDTO(workOrder);

        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWorkOrderTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = workOrderRepository.findAll().size();
        // set the field null
        workOrder.setWorkOrderType(null);

        // Create the WorkOrder, which fails.
        WorkOrderDTO workOrderDTO = workOrderMapper.workOrderToWorkOrderDTO(workOrder);

        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCompletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = workOrderRepository.findAll().size();
        // set the field null
        workOrder.setDateCompleted(null);

        // Create the WorkOrder, which fails.
        WorkOrderDTO workOrderDTO = workOrderMapper.workOrderToWorkOrderDTO(workOrder);

        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = workOrderRepository.findAll().size();
        // set the field null
        workOrder.setPriority(null);

        // Create the WorkOrder, which fails.
        WorkOrderDTO workOrderDTO = workOrderMapper.workOrderToWorkOrderDTO(workOrder);

        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstIsRequired() throws Exception {
        int databaseSizeBeforeTest = workOrderRepository.findAll().size();
        // set the field null
        workOrder.setEst(null);

        // Create the WorkOrder, which fails.
        WorkOrderDTO workOrderDTO = workOrderMapper.workOrderToWorkOrderDTO(workOrder);

        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrackerIsRequired() throws Exception {
        int databaseSizeBeforeTest = workOrderRepository.findAll().size();
        // set the field null
        workOrder.setTracker(null);

        // Create the WorkOrder, which fails.
        WorkOrderDTO workOrderDTO = workOrderMapper.workOrderToWorkOrderDTO(workOrder);

        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkOrders() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList
        restWorkOrderMockMvc.perform(get("/api/work-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].workOrderStatus").value(hasItem(DEFAULT_WORK_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].workOrderType").value(hasItem(DEFAULT_WORK_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dateCompleted").value(hasItem(DEFAULT_DATE_COMPLETED.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].est").value(hasItem(DEFAULT_EST.toString())))
            .andExpect(jsonPath("$.[*].estimatedHours").value(hasItem(DEFAULT_ESTIMATED_HOURS.intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].tracker").value(hasItem(DEFAULT_TRACKER.booleanValue())));
    }

    @Test
    @Transactional
    public void getWorkOrder() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get the workOrder
        restWorkOrderMockMvc.perform(get("/api/work-orders/{id}", workOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workOrder.getId().intValue()))
            .andExpect(jsonPath("$.workOrderStatus").value(DEFAULT_WORK_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.workOrderType").value(DEFAULT_WORK_ORDER_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dateCompleted").value(DEFAULT_DATE_COMPLETED.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.est").value(DEFAULT_EST.toString()))
            .andExpect(jsonPath("$.estimatedHours").value(DEFAULT_ESTIMATED_HOURS.intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.tracker").value(DEFAULT_TRACKER.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkOrder() throws Exception {
        // Get the workOrder
        restWorkOrderMockMvc.perform(get("/api/work-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkOrder() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);
        int databaseSizeBeforeUpdate = workOrderRepository.findAll().size();

        // Update the workOrder
        WorkOrder updatedWorkOrder = workOrderRepository.findOne(workOrder.getId());
        updatedWorkOrder
                .workOrderStatus(UPDATED_WORK_ORDER_STATUS)
                .workOrderType(UPDATED_WORK_ORDER_TYPE)
                .description(UPDATED_DESCRIPTION)
                .dateCompleted(UPDATED_DATE_COMPLETED)
                .priority(UPDATED_PRIORITY)
                .est(UPDATED_EST)
                .estimatedHours(UPDATED_ESTIMATED_HOURS)
                .comments(UPDATED_COMMENTS)
                .tracker(UPDATED_TRACKER);
        WorkOrderDTO workOrderDTO = workOrderMapper.workOrderToWorkOrderDTO(updatedWorkOrder);

        restWorkOrderMockMvc.perform(put("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isOk());

        // Validate the WorkOrder in the database
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeUpdate);
        WorkOrder testWorkOrder = workOrderList.get(workOrderList.size() - 1);
        assertThat(testWorkOrder.getWorkOrderStatus()).isEqualTo(UPDATED_WORK_ORDER_STATUS);
        assertThat(testWorkOrder.getWorkOrderType()).isEqualTo(UPDATED_WORK_ORDER_TYPE);
        assertThat(testWorkOrder.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkOrder.getDateCompleted()).isEqualTo(UPDATED_DATE_COMPLETED);
        assertThat(testWorkOrder.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testWorkOrder.getEst()).isEqualTo(UPDATED_EST);
        assertThat(testWorkOrder.getEstimatedHours()).isEqualTo(UPDATED_ESTIMATED_HOURS);
        assertThat(testWorkOrder.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testWorkOrder.isTracker()).isEqualTo(UPDATED_TRACKER);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkOrder() throws Exception {
        int databaseSizeBeforeUpdate = workOrderRepository.findAll().size();

        // Create the WorkOrder
        WorkOrderDTO workOrderDTO = workOrderMapper.workOrderToWorkOrderDTO(workOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkOrderMockMvc.perform(put("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkOrder in the database
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkOrder() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);
        int databaseSizeBeforeDelete = workOrderRepository.findAll().size();

        // Get the workOrder
        restWorkOrderMockMvc.perform(delete("/api/work-orders/{id}", workOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
