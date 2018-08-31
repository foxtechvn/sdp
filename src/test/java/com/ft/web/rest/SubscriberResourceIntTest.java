package com.ft.web.rest;

import com.ft.SdpApp;

import com.ft.domain.Subscriber;
import com.ft.repository.SubscriberRepository;
import com.ft.service.SubscriberService;
import com.ft.service.dto.SubscriberDTO;
import com.ft.service.mapper.SubscriberMapper;
import com.ft.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.ft.web.rest.TestUtil.sameInstant;
import static com.ft.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SubscriberResource REST controller.
 *
 * @see SubscriberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SdpApp.class)
public class SubscriberResourceIntTest {

    private static final String DEFAULT_MSISDN = "AAAAAAAAAA";
    private static final String UPDATED_MSISDN = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRIAL_CNT = 1;
    private static final Integer UPDATED_TRIAL_CNT = 2;

    private static final Integer DEFAULT_SUCCESS_CNT = 1;
    private static final Integer UPDATED_SUCCESS_CNT = 2;

    private static final Integer DEFAULT_STATE = -9;
    private static final Integer UPDATED_STATE = -8;

    private static final ZonedDateTime DEFAULT_JOIN_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_JOIN_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LEFT_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LEFT_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_EXPIRY_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRY_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CHARGE_LAST_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CHARGE_LAST_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CHARGE_NEXT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CHARGE_NEXT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CHARGE_LAST_SUCCESS = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CHARGE_LAST_SUCCESS = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_NOTIFY = -9;
    private static final Integer UPDATED_NOTIFY = -8;

    private static final ZonedDateTime DEFAULT_NOTIFY_LAST_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NOTIFY_LAST_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SubscriberRepository subscriberRepository;


    @Autowired
    private SubscriberMapper subscriberMapper;
    

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSubscriberMockMvc;

    private Subscriber subscriber;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubscriberResource subscriberResource = new SubscriberResource(subscriberService);
        this.restSubscriberMockMvc = MockMvcBuilders.standaloneSetup(subscriberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subscriber createEntity() {
        Subscriber subscriber = new Subscriber()
            .msisdn(DEFAULT_MSISDN)
            .productId(DEFAULT_PRODUCT_ID)
            .trialCnt(DEFAULT_TRIAL_CNT)
            .successCnt(DEFAULT_SUCCESS_CNT)
            .state(DEFAULT_STATE)
            .joinAt(DEFAULT_JOIN_AT)
            .leftAt(DEFAULT_LEFT_AT)
            .expiryTime(DEFAULT_EXPIRY_TIME)
            .chargeLastTime(DEFAULT_CHARGE_LAST_TIME)
            .chargeNextTime(DEFAULT_CHARGE_NEXT_TIME)
            .chargeLastSuccess(DEFAULT_CHARGE_LAST_SUCCESS)
            .notify(DEFAULT_NOTIFY)
            .notifyLastTime(DEFAULT_NOTIFY_LAST_TIME);
        return subscriber;
    }

    @Before
    public void initTest() {
        subscriberRepository.deleteAll();
        subscriber = createEntity();
    }

    @Test
    public void createSubscriber() throws Exception {
        int databaseSizeBeforeCreate = subscriberRepository.findAll().size();

        // Create the Subscriber
        SubscriberDTO subscriberDTO = subscriberMapper.toDto(subscriber);
        restSubscriberMockMvc.perform(post("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriberDTO)))
            .andExpect(status().isCreated());

        // Validate the Subscriber in the database
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeCreate + 1);
        Subscriber testSubscriber = subscriberList.get(subscriberList.size() - 1);
        assertThat(testSubscriber.getMsisdn()).isEqualTo(DEFAULT_MSISDN);
        assertThat(testSubscriber.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testSubscriber.getTrialCnt()).isEqualTo(DEFAULT_TRIAL_CNT);
        assertThat(testSubscriber.getSuccessCnt()).isEqualTo(DEFAULT_SUCCESS_CNT);
        assertThat(testSubscriber.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testSubscriber.getJoinAt()).isEqualTo(DEFAULT_JOIN_AT);
        assertThat(testSubscriber.getLeftAt()).isEqualTo(DEFAULT_LEFT_AT);
        assertThat(testSubscriber.getExpiryTime()).isEqualTo(DEFAULT_EXPIRY_TIME);
        assertThat(testSubscriber.getChargeLastTime()).isEqualTo(DEFAULT_CHARGE_LAST_TIME);
        assertThat(testSubscriber.getChargeNextTime()).isEqualTo(DEFAULT_CHARGE_NEXT_TIME);
        assertThat(testSubscriber.getChargeLastSuccess()).isEqualTo(DEFAULT_CHARGE_LAST_SUCCESS);
        assertThat(testSubscriber.getNotify()).isEqualTo(DEFAULT_NOTIFY);
        assertThat(testSubscriber.getNotifyLastTime()).isEqualTo(DEFAULT_NOTIFY_LAST_TIME);
    }

    @Test
    public void createSubscriberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subscriberRepository.findAll().size();

        // Create the Subscriber with an existing ID
        subscriber.setId("existing_id");
        SubscriberDTO subscriberDTO = subscriberMapper.toDto(subscriber);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriberMockMvc.perform(post("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subscriber in the database
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkMsisdnIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriberRepository.findAll().size();
        // set the field null
        subscriber.setMsisdn(null);

        // Create the Subscriber, which fails.
        SubscriberDTO subscriberDTO = subscriberMapper.toDto(subscriber);

        restSubscriberMockMvc.perform(post("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriberDTO)))
            .andExpect(status().isBadRequest());

        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProductIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriberRepository.findAll().size();
        // set the field null
        subscriber.setProductId(null);

        // Create the Subscriber, which fails.
        SubscriberDTO subscriberDTO = subscriberMapper.toDto(subscriber);

        restSubscriberMockMvc.perform(post("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriberDTO)))
            .andExpect(status().isBadRequest());

        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriberRepository.findAll().size();
        // set the field null
        subscriber.setState(null);

        // Create the Subscriber, which fails.
        SubscriberDTO subscriberDTO = subscriberMapper.toDto(subscriber);

        restSubscriberMockMvc.perform(post("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriberDTO)))
            .andExpect(status().isBadRequest());

        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNotifyIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriberRepository.findAll().size();
        // set the field null
        subscriber.setNotify(null);

        // Create the Subscriber, which fails.
        SubscriberDTO subscriberDTO = subscriberMapper.toDto(subscriber);

        restSubscriberMockMvc.perform(post("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriberDTO)))
            .andExpect(status().isBadRequest());

        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSubscribers() throws Exception {
        // Initialize the database
        subscriberRepository.save(subscriber);

        // Get all the subscriberList
        restSubscriberMockMvc.perform(get("/api/subscribers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriber.getId())))
            .andExpect(jsonPath("$.[*].msisdn").value(hasItem(DEFAULT_MSISDN.toString())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
            .andExpect(jsonPath("$.[*].trialCnt").value(hasItem(DEFAULT_TRIAL_CNT)))
            .andExpect(jsonPath("$.[*].successCnt").value(hasItem(DEFAULT_SUCCESS_CNT)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].joinAt").value(hasItem(sameInstant(DEFAULT_JOIN_AT))))
            .andExpect(jsonPath("$.[*].leftAt").value(hasItem(sameInstant(DEFAULT_LEFT_AT))))
            .andExpect(jsonPath("$.[*].expiryTime").value(hasItem(sameInstant(DEFAULT_EXPIRY_TIME))))
            .andExpect(jsonPath("$.[*].chargeLastTime").value(hasItem(sameInstant(DEFAULT_CHARGE_LAST_TIME))))
            .andExpect(jsonPath("$.[*].chargeNextTime").value(hasItem(sameInstant(DEFAULT_CHARGE_NEXT_TIME))))
            .andExpect(jsonPath("$.[*].chargeLastSuccess").value(hasItem(sameInstant(DEFAULT_CHARGE_LAST_SUCCESS))))
            .andExpect(jsonPath("$.[*].notify").value(hasItem(DEFAULT_NOTIFY)))
            .andExpect(jsonPath("$.[*].notifyLastTime").value(hasItem(sameInstant(DEFAULT_NOTIFY_LAST_TIME))));
    }
    

    @Test
    public void getSubscriber() throws Exception {
        // Initialize the database
        subscriberRepository.save(subscriber);

        // Get the subscriber
        restSubscriberMockMvc.perform(get("/api/subscribers/{id}", subscriber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subscriber.getId()))
            .andExpect(jsonPath("$.msisdn").value(DEFAULT_MSISDN.toString()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.trialCnt").value(DEFAULT_TRIAL_CNT))
            .andExpect(jsonPath("$.successCnt").value(DEFAULT_SUCCESS_CNT))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.joinAt").value(sameInstant(DEFAULT_JOIN_AT)))
            .andExpect(jsonPath("$.leftAt").value(sameInstant(DEFAULT_LEFT_AT)))
            .andExpect(jsonPath("$.expiryTime").value(sameInstant(DEFAULT_EXPIRY_TIME)))
            .andExpect(jsonPath("$.chargeLastTime").value(sameInstant(DEFAULT_CHARGE_LAST_TIME)))
            .andExpect(jsonPath("$.chargeNextTime").value(sameInstant(DEFAULT_CHARGE_NEXT_TIME)))
            .andExpect(jsonPath("$.chargeLastSuccess").value(sameInstant(DEFAULT_CHARGE_LAST_SUCCESS)))
            .andExpect(jsonPath("$.notify").value(DEFAULT_NOTIFY))
            .andExpect(jsonPath("$.notifyLastTime").value(sameInstant(DEFAULT_NOTIFY_LAST_TIME)));
    }
    @Test
    public void getNonExistingSubscriber() throws Exception {
        // Get the subscriber
        restSubscriberMockMvc.perform(get("/api/subscribers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSubscriber() throws Exception {
        // Initialize the database
        subscriberRepository.save(subscriber);

        int databaseSizeBeforeUpdate = subscriberRepository.findAll().size();

        // Update the subscriber
        Subscriber updatedSubscriber = subscriberRepository.findById(subscriber.getId()).get();
        updatedSubscriber
            .msisdn(UPDATED_MSISDN)
            .productId(UPDATED_PRODUCT_ID)
            .trialCnt(UPDATED_TRIAL_CNT)
            .successCnt(UPDATED_SUCCESS_CNT)
            .state(UPDATED_STATE)
            .joinAt(UPDATED_JOIN_AT)
            .leftAt(UPDATED_LEFT_AT)
            .expiryTime(UPDATED_EXPIRY_TIME)
            .chargeLastTime(UPDATED_CHARGE_LAST_TIME)
            .chargeNextTime(UPDATED_CHARGE_NEXT_TIME)
            .chargeLastSuccess(UPDATED_CHARGE_LAST_SUCCESS)
            .notify(UPDATED_NOTIFY)
            .notifyLastTime(UPDATED_NOTIFY_LAST_TIME);
        SubscriberDTO subscriberDTO = subscriberMapper.toDto(updatedSubscriber);

        restSubscriberMockMvc.perform(put("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriberDTO)))
            .andExpect(status().isOk());

        // Validate the Subscriber in the database
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeUpdate);
        Subscriber testSubscriber = subscriberList.get(subscriberList.size() - 1);
        assertThat(testSubscriber.getMsisdn()).isEqualTo(UPDATED_MSISDN);
        assertThat(testSubscriber.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testSubscriber.getTrialCnt()).isEqualTo(UPDATED_TRIAL_CNT);
        assertThat(testSubscriber.getSuccessCnt()).isEqualTo(UPDATED_SUCCESS_CNT);
        assertThat(testSubscriber.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testSubscriber.getJoinAt()).isEqualTo(UPDATED_JOIN_AT);
        assertThat(testSubscriber.getLeftAt()).isEqualTo(UPDATED_LEFT_AT);
        assertThat(testSubscriber.getExpiryTime()).isEqualTo(UPDATED_EXPIRY_TIME);
        assertThat(testSubscriber.getChargeLastTime()).isEqualTo(UPDATED_CHARGE_LAST_TIME);
        assertThat(testSubscriber.getChargeNextTime()).isEqualTo(UPDATED_CHARGE_NEXT_TIME);
        assertThat(testSubscriber.getChargeLastSuccess()).isEqualTo(UPDATED_CHARGE_LAST_SUCCESS);
        assertThat(testSubscriber.getNotify()).isEqualTo(UPDATED_NOTIFY);
        assertThat(testSubscriber.getNotifyLastTime()).isEqualTo(UPDATED_NOTIFY_LAST_TIME);
    }

    @Test
    public void updateNonExistingSubscriber() throws Exception {
        int databaseSizeBeforeUpdate = subscriberRepository.findAll().size();

        // Create the Subscriber
        SubscriberDTO subscriberDTO = subscriberMapper.toDto(subscriber);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restSubscriberMockMvc.perform(put("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subscriber in the database
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSubscriber() throws Exception {
        // Initialize the database
        subscriberRepository.save(subscriber);

        int databaseSizeBeforeDelete = subscriberRepository.findAll().size();

        // Get the subscriber
        restSubscriberMockMvc.perform(delete("/api/subscribers/{id}", subscriber.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subscriber.class);
        Subscriber subscriber1 = new Subscriber();
        subscriber1.setId("id1");
        Subscriber subscriber2 = new Subscriber();
        subscriber2.setId(subscriber1.getId());
        assertThat(subscriber1).isEqualTo(subscriber2);
        subscriber2.setId("id2");
        assertThat(subscriber1).isNotEqualTo(subscriber2);
        subscriber1.setId(null);
        assertThat(subscriber1).isNotEqualTo(subscriber2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriberDTO.class);
        SubscriberDTO subscriberDTO1 = new SubscriberDTO();
        subscriberDTO1.setId("id1");
        SubscriberDTO subscriberDTO2 = new SubscriberDTO();
        assertThat(subscriberDTO1).isNotEqualTo(subscriberDTO2);
        subscriberDTO2.setId(subscriberDTO1.getId());
        assertThat(subscriberDTO1).isEqualTo(subscriberDTO2);
        subscriberDTO2.setId("id2");
        assertThat(subscriberDTO1).isNotEqualTo(subscriberDTO2);
        subscriberDTO1.setId(null);
        assertThat(subscriberDTO1).isNotEqualTo(subscriberDTO2);
    }
}
