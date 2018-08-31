package com.ft.web.rest;

import com.ft.SdpApp;

import com.ft.domain.Sms;
import com.ft.repository.SmsRepository;
import com.ft.service.SmsService;
import com.ft.service.dto.SmsDTO;
import com.ft.service.mapper.SmsMapper;
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
 * Test class for the SmsResource REST controller.
 *
 * @see SmsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SdpApp.class)
public class SmsResourceIntTest {

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_SUBMIT_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SUBMIT_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_EXPIRED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELIVERED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELIVERED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_STATE = -9;
    private static final Integer UPDATED_STATE = -8;

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TNX_ID = "AAAAAAAAAA";
    private static final String UPDATED_TNX_ID = "BBBBBBBBBB";

    @Autowired
    private SmsRepository smsRepository;


    @Autowired
    private SmsMapper smsMapper;
    

    @Autowired
    private SmsService smsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSmsMockMvc;

    private Sms sms;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SmsResource smsResource = new SmsResource(smsService);
        this.restSmsMockMvc = MockMvcBuilders.standaloneSetup(smsResource)
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
    public static Sms createEntity() {
        Sms sms = new Sms()
            .source(DEFAULT_SOURCE)
            .destination(DEFAULT_DESTINATION)
            .text(DEFAULT_TEXT)
            .submitAt(DEFAULT_SUBMIT_AT)
            .expiredAt(DEFAULT_EXPIRED_AT)
            .deliveredAt(DEFAULT_DELIVERED_AT)
            .state(DEFAULT_STATE)
            .productId(DEFAULT_PRODUCT_ID)
            .contentId(DEFAULT_CONTENT_ID)
            .tnxId(DEFAULT_TNX_ID);
        return sms;
    }

    @Before
    public void initTest() {
        smsRepository.deleteAll();
        sms = createEntity();
    }

    @Test
    public void createSms() throws Exception {
        int databaseSizeBeforeCreate = smsRepository.findAll().size();

        // Create the Sms
        SmsDTO smsDTO = smsMapper.toDto(sms);
        restSmsMockMvc.perform(post("/api/sms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsDTO)))
            .andExpect(status().isCreated());

        // Validate the Sms in the database
        List<Sms> smsList = smsRepository.findAll();
        assertThat(smsList).hasSize(databaseSizeBeforeCreate + 1);
        Sms testSms = smsList.get(smsList.size() - 1);
        assertThat(testSms.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testSms.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testSms.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testSms.getSubmitAt()).isEqualTo(DEFAULT_SUBMIT_AT);
        assertThat(testSms.getExpiredAt()).isEqualTo(DEFAULT_EXPIRED_AT);
        assertThat(testSms.getDeliveredAt()).isEqualTo(DEFAULT_DELIVERED_AT);
        assertThat(testSms.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testSms.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testSms.getContentId()).isEqualTo(DEFAULT_CONTENT_ID);
        assertThat(testSms.getTnxId()).isEqualTo(DEFAULT_TNX_ID);
    }

    @Test
    public void createSmsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = smsRepository.findAll().size();

        // Create the Sms with an existing ID
        sms.setId("existing_id");
        SmsDTO smsDTO = smsMapper.toDto(sms);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSmsMockMvc.perform(post("/api/sms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sms in the database
        List<Sms> smsList = smsRepository.findAll();
        assertThat(smsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsRepository.findAll().size();
        // set the field null
        sms.setState(null);

        // Create the Sms, which fails.
        SmsDTO smsDTO = smsMapper.toDto(sms);

        restSmsMockMvc.perform(post("/api/sms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsDTO)))
            .andExpect(status().isBadRequest());

        List<Sms> smsList = smsRepository.findAll();
        assertThat(smsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSms() throws Exception {
        // Initialize the database
        smsRepository.save(sms);

        // Get all the smsList
        restSmsMockMvc.perform(get("/api/sms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sms.getId())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].submitAt").value(hasItem(sameInstant(DEFAULT_SUBMIT_AT))))
            .andExpect(jsonPath("$.[*].expiredAt").value(hasItem(sameInstant(DEFAULT_EXPIRED_AT))))
            .andExpect(jsonPath("$.[*].deliveredAt").value(hasItem(sameInstant(DEFAULT_DELIVERED_AT))))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
            .andExpect(jsonPath("$.[*].contentId").value(hasItem(DEFAULT_CONTENT_ID.toString())))
            .andExpect(jsonPath("$.[*].tnxId").value(hasItem(DEFAULT_TNX_ID.toString())));
    }
    

    @Test
    public void getSms() throws Exception {
        // Initialize the database
        smsRepository.save(sms);

        // Get the sms
        restSmsMockMvc.perform(get("/api/sms/{id}", sms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sms.getId()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.submitAt").value(sameInstant(DEFAULT_SUBMIT_AT)))
            .andExpect(jsonPath("$.expiredAt").value(sameInstant(DEFAULT_EXPIRED_AT)))
            .andExpect(jsonPath("$.deliveredAt").value(sameInstant(DEFAULT_DELIVERED_AT)))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.contentId").value(DEFAULT_CONTENT_ID.toString()))
            .andExpect(jsonPath("$.tnxId").value(DEFAULT_TNX_ID.toString()));
    }
    @Test
    public void getNonExistingSms() throws Exception {
        // Get the sms
        restSmsMockMvc.perform(get("/api/sms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSms() throws Exception {
        // Initialize the database
        smsRepository.save(sms);

        int databaseSizeBeforeUpdate = smsRepository.findAll().size();

        // Update the sms
        Sms updatedSms = smsRepository.findById(sms.getId()).get();
        updatedSms
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .text(UPDATED_TEXT)
            .submitAt(UPDATED_SUBMIT_AT)
            .expiredAt(UPDATED_EXPIRED_AT)
            .deliveredAt(UPDATED_DELIVERED_AT)
            .state(UPDATED_STATE)
            .productId(UPDATED_PRODUCT_ID)
            .contentId(UPDATED_CONTENT_ID)
            .tnxId(UPDATED_TNX_ID);
        SmsDTO smsDTO = smsMapper.toDto(updatedSms);

        restSmsMockMvc.perform(put("/api/sms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsDTO)))
            .andExpect(status().isOk());

        // Validate the Sms in the database
        List<Sms> smsList = smsRepository.findAll();
        assertThat(smsList).hasSize(databaseSizeBeforeUpdate);
        Sms testSms = smsList.get(smsList.size() - 1);
        assertThat(testSms.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testSms.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testSms.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testSms.getSubmitAt()).isEqualTo(UPDATED_SUBMIT_AT);
        assertThat(testSms.getExpiredAt()).isEqualTo(UPDATED_EXPIRED_AT);
        assertThat(testSms.getDeliveredAt()).isEqualTo(UPDATED_DELIVERED_AT);
        assertThat(testSms.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testSms.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testSms.getContentId()).isEqualTo(UPDATED_CONTENT_ID);
        assertThat(testSms.getTnxId()).isEqualTo(UPDATED_TNX_ID);
    }

    @Test
    public void updateNonExistingSms() throws Exception {
        int databaseSizeBeforeUpdate = smsRepository.findAll().size();

        // Create the Sms
        SmsDTO smsDTO = smsMapper.toDto(sms);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restSmsMockMvc.perform(put("/api/sms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sms in the database
        List<Sms> smsList = smsRepository.findAll();
        assertThat(smsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSms() throws Exception {
        // Initialize the database
        smsRepository.save(sms);

        int databaseSizeBeforeDelete = smsRepository.findAll().size();

        // Get the sms
        restSmsMockMvc.perform(delete("/api/sms/{id}", sms.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sms> smsList = smsRepository.findAll();
        assertThat(smsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sms.class);
        Sms sms1 = new Sms();
        sms1.setId("id1");
        Sms sms2 = new Sms();
        sms2.setId(sms1.getId());
        assertThat(sms1).isEqualTo(sms2);
        sms2.setId("id2");
        assertThat(sms1).isNotEqualTo(sms2);
        sms1.setId(null);
        assertThat(sms1).isNotEqualTo(sms2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmsDTO.class);
        SmsDTO smsDTO1 = new SmsDTO();
        smsDTO1.setId("id1");
        SmsDTO smsDTO2 = new SmsDTO();
        assertThat(smsDTO1).isNotEqualTo(smsDTO2);
        smsDTO2.setId(smsDTO1.getId());
        assertThat(smsDTO1).isEqualTo(smsDTO2);
        smsDTO2.setId("id2");
        assertThat(smsDTO1).isNotEqualTo(smsDTO2);
        smsDTO1.setId(null);
        assertThat(smsDTO1).isNotEqualTo(smsDTO2);
    }
}
