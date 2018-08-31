package com.ft.web.rest;

import com.ft.SdpApp;

import com.ft.domain.SmsContent;
import com.ft.repository.SmsContentRepository;
import com.ft.service.SmsContentService;
import com.ft.service.dto.SmsContentDTO;
import com.ft.service.mapper.SmsContentMapper;
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
 * Test class for the SmsContentResource REST controller.
 *
 * @see SmsContentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SdpApp.class)
public class SmsContentResourceIntTest {

    private static final ZonedDateTime DEFAULT_START_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_EXPIRED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATE = -9;
    private static final Integer UPDATED_STATE = -8;

    @Autowired
    private SmsContentRepository smsContentRepository;


    @Autowired
    private SmsContentMapper smsContentMapper;
    

    @Autowired
    private SmsContentService smsContentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSmsContentMockMvc;

    private SmsContent smsContent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SmsContentResource smsContentResource = new SmsContentResource(smsContentService);
        this.restSmsContentMockMvc = MockMvcBuilders.standaloneSetup(smsContentResource)
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
    public static SmsContent createEntity() {
        SmsContent smsContent = new SmsContent()
            .startAt(DEFAULT_START_AT)
            .expiredAt(DEFAULT_EXPIRED_AT)
            .message(DEFAULT_MESSAGE)
            .productId(DEFAULT_PRODUCT_ID)
            .state(DEFAULT_STATE);
        return smsContent;
    }

    @Before
    public void initTest() {
        smsContentRepository.deleteAll();
        smsContent = createEntity();
    }

    @Test
    public void createSmsContent() throws Exception {
        int databaseSizeBeforeCreate = smsContentRepository.findAll().size();

        // Create the SmsContent
        SmsContentDTO smsContentDTO = smsContentMapper.toDto(smsContent);
        restSmsContentMockMvc.perform(post("/api/sms-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsContentDTO)))
            .andExpect(status().isCreated());

        // Validate the SmsContent in the database
        List<SmsContent> smsContentList = smsContentRepository.findAll();
        assertThat(smsContentList).hasSize(databaseSizeBeforeCreate + 1);
        SmsContent testSmsContent = smsContentList.get(smsContentList.size() - 1);
        assertThat(testSmsContent.getStartAt()).isEqualTo(DEFAULT_START_AT);
        assertThat(testSmsContent.getExpiredAt()).isEqualTo(DEFAULT_EXPIRED_AT);
        assertThat(testSmsContent.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testSmsContent.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testSmsContent.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    public void createSmsContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = smsContentRepository.findAll().size();

        // Create the SmsContent with an existing ID
        smsContent.setId("existing_id");
        SmsContentDTO smsContentDTO = smsContentMapper.toDto(smsContent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSmsContentMockMvc.perform(post("/api/sms-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SmsContent in the database
        List<SmsContent> smsContentList = smsContentRepository.findAll();
        assertThat(smsContentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsContentRepository.findAll().size();
        // set the field null
        smsContent.setState(null);

        // Create the SmsContent, which fails.
        SmsContentDTO smsContentDTO = smsContentMapper.toDto(smsContent);

        restSmsContentMockMvc.perform(post("/api/sms-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsContentDTO)))
            .andExpect(status().isBadRequest());

        List<SmsContent> smsContentList = smsContentRepository.findAll();
        assertThat(smsContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSmsContents() throws Exception {
        // Initialize the database
        smsContentRepository.save(smsContent);

        // Get all the smsContentList
        restSmsContentMockMvc.perform(get("/api/sms-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smsContent.getId())))
            .andExpect(jsonPath("$.[*].startAt").value(hasItem(sameInstant(DEFAULT_START_AT))))
            .andExpect(jsonPath("$.[*].expiredAt").value(hasItem(sameInstant(DEFAULT_EXPIRED_AT))))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    

    @Test
    public void getSmsContent() throws Exception {
        // Initialize the database
        smsContentRepository.save(smsContent);

        // Get the smsContent
        restSmsContentMockMvc.perform(get("/api/sms-contents/{id}", smsContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(smsContent.getId()))
            .andExpect(jsonPath("$.startAt").value(sameInstant(DEFAULT_START_AT)))
            .andExpect(jsonPath("$.expiredAt").value(sameInstant(DEFAULT_EXPIRED_AT)))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }
    @Test
    public void getNonExistingSmsContent() throws Exception {
        // Get the smsContent
        restSmsContentMockMvc.perform(get("/api/sms-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSmsContent() throws Exception {
        // Initialize the database
        smsContentRepository.save(smsContent);

        int databaseSizeBeforeUpdate = smsContentRepository.findAll().size();

        // Update the smsContent
        SmsContent updatedSmsContent = smsContentRepository.findById(smsContent.getId()).get();
        updatedSmsContent
            .startAt(UPDATED_START_AT)
            .expiredAt(UPDATED_EXPIRED_AT)
            .message(UPDATED_MESSAGE)
            .productId(UPDATED_PRODUCT_ID)
            .state(UPDATED_STATE);
        SmsContentDTO smsContentDTO = smsContentMapper.toDto(updatedSmsContent);

        restSmsContentMockMvc.perform(put("/api/sms-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsContentDTO)))
            .andExpect(status().isOk());

        // Validate the SmsContent in the database
        List<SmsContent> smsContentList = smsContentRepository.findAll();
        assertThat(smsContentList).hasSize(databaseSizeBeforeUpdate);
        SmsContent testSmsContent = smsContentList.get(smsContentList.size() - 1);
        assertThat(testSmsContent.getStartAt()).isEqualTo(UPDATED_START_AT);
        assertThat(testSmsContent.getExpiredAt()).isEqualTo(UPDATED_EXPIRED_AT);
        assertThat(testSmsContent.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testSmsContent.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testSmsContent.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    public void updateNonExistingSmsContent() throws Exception {
        int databaseSizeBeforeUpdate = smsContentRepository.findAll().size();

        // Create the SmsContent
        SmsContentDTO smsContentDTO = smsContentMapper.toDto(smsContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restSmsContentMockMvc.perform(put("/api/sms-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SmsContent in the database
        List<SmsContent> smsContentList = smsContentRepository.findAll();
        assertThat(smsContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSmsContent() throws Exception {
        // Initialize the database
        smsContentRepository.save(smsContent);

        int databaseSizeBeforeDelete = smsContentRepository.findAll().size();

        // Get the smsContent
        restSmsContentMockMvc.perform(delete("/api/sms-contents/{id}", smsContent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SmsContent> smsContentList = smsContentRepository.findAll();
        assertThat(smsContentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmsContent.class);
        SmsContent smsContent1 = new SmsContent();
        smsContent1.setId("id1");
        SmsContent smsContent2 = new SmsContent();
        smsContent2.setId(smsContent1.getId());
        assertThat(smsContent1).isEqualTo(smsContent2);
        smsContent2.setId("id2");
        assertThat(smsContent1).isNotEqualTo(smsContent2);
        smsContent1.setId(null);
        assertThat(smsContent1).isNotEqualTo(smsContent2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmsContentDTO.class);
        SmsContentDTO smsContentDTO1 = new SmsContentDTO();
        smsContentDTO1.setId("id1");
        SmsContentDTO smsContentDTO2 = new SmsContentDTO();
        assertThat(smsContentDTO1).isNotEqualTo(smsContentDTO2);
        smsContentDTO2.setId(smsContentDTO1.getId());
        assertThat(smsContentDTO1).isEqualTo(smsContentDTO2);
        smsContentDTO2.setId("id2");
        assertThat(smsContentDTO1).isNotEqualTo(smsContentDTO2);
        smsContentDTO1.setId(null);
        assertThat(smsContentDTO1).isNotEqualTo(smsContentDTO2);
    }
}
