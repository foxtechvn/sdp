package com.ft.web.rest;

import com.ft.SdpApp;

import com.ft.domain.Cdr;
import com.ft.repository.CdrRepository;
import com.ft.service.CdrService;
import com.ft.service.dto.CdrDTO;
import com.ft.service.mapper.CdrMapper;
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
 * Test class for the CdrResource REST controller.
 *
 * @see CdrResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SdpApp.class)
public class CdrResourceIntTest {

    private static final String DEFAULT_MSISDN = "AAAAAAAAAA";
    private static final String UPDATED_MSISDN = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATE = -9;
    private static final Integer UPDATED_STATE = -8;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final ZonedDateTime DEFAULT_REQUEST_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REQUEST_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RESPONSE_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESPONSE_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_ID = "BBBBBBBBBB";

    @Autowired
    private CdrRepository cdrRepository;


    @Autowired
    private CdrMapper cdrMapper;
    

    @Autowired
    private CdrService cdrService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCdrMockMvc;

    private Cdr cdr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CdrResource cdrResource = new CdrResource(cdrService);
        this.restCdrMockMvc = MockMvcBuilders.standaloneSetup(cdrResource)
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
    public static Cdr createEntity() {
        Cdr cdr = new Cdr()
            .msisdn(DEFAULT_MSISDN)
            .state(DEFAULT_STATE)
            .amount(DEFAULT_AMOUNT)
            .requestAt(DEFAULT_REQUEST_AT)
            .responseAt(DEFAULT_RESPONSE_AT)
            .productId(DEFAULT_PRODUCT_ID)
            .contentId(DEFAULT_CONTENT_ID);
        return cdr;
    }

    @Before
    public void initTest() {
        cdrRepository.deleteAll();
        cdr = createEntity();
    }

    @Test
    public void createCdr() throws Exception {
        int databaseSizeBeforeCreate = cdrRepository.findAll().size();

        // Create the Cdr
        CdrDTO cdrDTO = cdrMapper.toDto(cdr);
        restCdrMockMvc.perform(post("/api/cdrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdrDTO)))
            .andExpect(status().isCreated());

        // Validate the Cdr in the database
        List<Cdr> cdrList = cdrRepository.findAll();
        assertThat(cdrList).hasSize(databaseSizeBeforeCreate + 1);
        Cdr testCdr = cdrList.get(cdrList.size() - 1);
        assertThat(testCdr.getMsisdn()).isEqualTo(DEFAULT_MSISDN);
        assertThat(testCdr.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCdr.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCdr.getRequestAt()).isEqualTo(DEFAULT_REQUEST_AT);
        assertThat(testCdr.getResponseAt()).isEqualTo(DEFAULT_RESPONSE_AT);
        assertThat(testCdr.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testCdr.getContentId()).isEqualTo(DEFAULT_CONTENT_ID);
    }

    @Test
    public void createCdrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cdrRepository.findAll().size();

        // Create the Cdr with an existing ID
        cdr.setId("existing_id");
        CdrDTO cdrDTO = cdrMapper.toDto(cdr);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCdrMockMvc.perform(post("/api/cdrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdrDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cdr in the database
        List<Cdr> cdrList = cdrRepository.findAll();
        assertThat(cdrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkMsisdnIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdrRepository.findAll().size();
        // set the field null
        cdr.setMsisdn(null);

        // Create the Cdr, which fails.
        CdrDTO cdrDTO = cdrMapper.toDto(cdr);

        restCdrMockMvc.perform(post("/api/cdrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdrDTO)))
            .andExpect(status().isBadRequest());

        List<Cdr> cdrList = cdrRepository.findAll();
        assertThat(cdrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdrRepository.findAll().size();
        // set the field null
        cdr.setAmount(null);

        // Create the Cdr, which fails.
        CdrDTO cdrDTO = cdrMapper.toDto(cdr);

        restCdrMockMvc.perform(post("/api/cdrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdrDTO)))
            .andExpect(status().isBadRequest());

        List<Cdr> cdrList = cdrRepository.findAll();
        assertThat(cdrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCdrs() throws Exception {
        // Initialize the database
        cdrRepository.save(cdr);

        // Get all the cdrList
        restCdrMockMvc.perform(get("/api/cdrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cdr.getId())))
            .andExpect(jsonPath("$.[*].msisdn").value(hasItem(DEFAULT_MSISDN.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].requestAt").value(hasItem(sameInstant(DEFAULT_REQUEST_AT))))
            .andExpect(jsonPath("$.[*].responseAt").value(hasItem(sameInstant(DEFAULT_RESPONSE_AT))))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
            .andExpect(jsonPath("$.[*].contentId").value(hasItem(DEFAULT_CONTENT_ID.toString())));
    }
    

    @Test
    public void getCdr() throws Exception {
        // Initialize the database
        cdrRepository.save(cdr);

        // Get the cdr
        restCdrMockMvc.perform(get("/api/cdrs/{id}", cdr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cdr.getId()))
            .andExpect(jsonPath("$.msisdn").value(DEFAULT_MSISDN.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.requestAt").value(sameInstant(DEFAULT_REQUEST_AT)))
            .andExpect(jsonPath("$.responseAt").value(sameInstant(DEFAULT_RESPONSE_AT)))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.contentId").value(DEFAULT_CONTENT_ID.toString()));
    }
    @Test
    public void getNonExistingCdr() throws Exception {
        // Get the cdr
        restCdrMockMvc.perform(get("/api/cdrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCdr() throws Exception {
        // Initialize the database
        cdrRepository.save(cdr);

        int databaseSizeBeforeUpdate = cdrRepository.findAll().size();

        // Update the cdr
        Cdr updatedCdr = cdrRepository.findById(cdr.getId()).get();
        updatedCdr
            .msisdn(UPDATED_MSISDN)
            .state(UPDATED_STATE)
            .amount(UPDATED_AMOUNT)
            .requestAt(UPDATED_REQUEST_AT)
            .responseAt(UPDATED_RESPONSE_AT)
            .productId(UPDATED_PRODUCT_ID)
            .contentId(UPDATED_CONTENT_ID);
        CdrDTO cdrDTO = cdrMapper.toDto(updatedCdr);

        restCdrMockMvc.perform(put("/api/cdrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdrDTO)))
            .andExpect(status().isOk());

        // Validate the Cdr in the database
        List<Cdr> cdrList = cdrRepository.findAll();
        assertThat(cdrList).hasSize(databaseSizeBeforeUpdate);
        Cdr testCdr = cdrList.get(cdrList.size() - 1);
        assertThat(testCdr.getMsisdn()).isEqualTo(UPDATED_MSISDN);
        assertThat(testCdr.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCdr.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCdr.getRequestAt()).isEqualTo(UPDATED_REQUEST_AT);
        assertThat(testCdr.getResponseAt()).isEqualTo(UPDATED_RESPONSE_AT);
        assertThat(testCdr.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testCdr.getContentId()).isEqualTo(UPDATED_CONTENT_ID);
    }

    @Test
    public void updateNonExistingCdr() throws Exception {
        int databaseSizeBeforeUpdate = cdrRepository.findAll().size();

        // Create the Cdr
        CdrDTO cdrDTO = cdrMapper.toDto(cdr);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCdrMockMvc.perform(put("/api/cdrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdrDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cdr in the database
        List<Cdr> cdrList = cdrRepository.findAll();
        assertThat(cdrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCdr() throws Exception {
        // Initialize the database
        cdrRepository.save(cdr);

        int databaseSizeBeforeDelete = cdrRepository.findAll().size();

        // Get the cdr
        restCdrMockMvc.perform(delete("/api/cdrs/{id}", cdr.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cdr> cdrList = cdrRepository.findAll();
        assertThat(cdrList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cdr.class);
        Cdr cdr1 = new Cdr();
        cdr1.setId("id1");
        Cdr cdr2 = new Cdr();
        cdr2.setId(cdr1.getId());
        assertThat(cdr1).isEqualTo(cdr2);
        cdr2.setId("id2");
        assertThat(cdr1).isNotEqualTo(cdr2);
        cdr1.setId(null);
        assertThat(cdr1).isNotEqualTo(cdr2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CdrDTO.class);
        CdrDTO cdrDTO1 = new CdrDTO();
        cdrDTO1.setId("id1");
        CdrDTO cdrDTO2 = new CdrDTO();
        assertThat(cdrDTO1).isNotEqualTo(cdrDTO2);
        cdrDTO2.setId(cdrDTO1.getId());
        assertThat(cdrDTO1).isEqualTo(cdrDTO2);
        cdrDTO2.setId("id2");
        assertThat(cdrDTO1).isNotEqualTo(cdrDTO2);
        cdrDTO1.setId(null);
        assertThat(cdrDTO1).isNotEqualTo(cdrDTO2);
    }
}
