package com.ft.web.rest;

import com.ft.SdpApp;

import com.ft.domain.Dnd;
import com.ft.repository.DndRepository;
import com.ft.service.DndService;
import com.ft.service.dto.DndDTO;
import com.ft.service.mapper.DndMapper;
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
 * Test class for the DndResource REST controller.
 *
 * @see DndResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SdpApp.class)
public class DndResourceIntTest {

    private static final ZonedDateTime DEFAULT_JOIN_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_JOIN_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_JOIN_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_JOIN_CHANNEL = "BBBBBBBBBB";

    @Autowired
    private DndRepository dndRepository;


    @Autowired
    private DndMapper dndMapper;
    

    @Autowired
    private DndService dndService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDndMockMvc;

    private Dnd dnd;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DndResource dndResource = new DndResource(dndService);
        this.restDndMockMvc = MockMvcBuilders.standaloneSetup(dndResource)
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
    public static Dnd createEntity() {
        Dnd dnd = new Dnd()
            .joinAt(DEFAULT_JOIN_AT)
            .joinChannel(DEFAULT_JOIN_CHANNEL);
        return dnd;
    }

    @Before
    public void initTest() {
        dndRepository.deleteAll();
        dnd = createEntity();
    }

    @Test
    public void createDnd() throws Exception {
        int databaseSizeBeforeCreate = dndRepository.findAll().size();

        // Create the Dnd
        DndDTO dndDTO = dndMapper.toDto(dnd);
        restDndMockMvc.perform(post("/api/dnds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dndDTO)))
            .andExpect(status().isCreated());

        // Validate the Dnd in the database
        List<Dnd> dndList = dndRepository.findAll();
        assertThat(dndList).hasSize(databaseSizeBeforeCreate + 1);
        Dnd testDnd = dndList.get(dndList.size() - 1);
        assertThat(testDnd.getJoinAt()).isEqualTo(DEFAULT_JOIN_AT);
        assertThat(testDnd.getJoinChannel()).isEqualTo(DEFAULT_JOIN_CHANNEL);
    }

    @Test
    public void createDndWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dndRepository.findAll().size();

        // Create the Dnd with an existing ID
        dnd.setId("existing_id");
        DndDTO dndDTO = dndMapper.toDto(dnd);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDndMockMvc.perform(post("/api/dnds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dndDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dnd in the database
        List<Dnd> dndList = dndRepository.findAll();
        assertThat(dndList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllDnds() throws Exception {
        // Initialize the database
        dndRepository.save(dnd);

        // Get all the dndList
        restDndMockMvc.perform(get("/api/dnds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dnd.getId())))
            .andExpect(jsonPath("$.[*].joinAt").value(hasItem(sameInstant(DEFAULT_JOIN_AT))))
            .andExpect(jsonPath("$.[*].joinChannel").value(hasItem(DEFAULT_JOIN_CHANNEL.toString())));
    }
    

    @Test
    public void getDnd() throws Exception {
        // Initialize the database
        dndRepository.save(dnd);

        // Get the dnd
        restDndMockMvc.perform(get("/api/dnds/{id}", dnd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dnd.getId()))
            .andExpect(jsonPath("$.joinAt").value(sameInstant(DEFAULT_JOIN_AT)))
            .andExpect(jsonPath("$.joinChannel").value(DEFAULT_JOIN_CHANNEL.toString()));
    }
    @Test
    public void getNonExistingDnd() throws Exception {
        // Get the dnd
        restDndMockMvc.perform(get("/api/dnds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDnd() throws Exception {
        // Initialize the database
        dndRepository.save(dnd);

        int databaseSizeBeforeUpdate = dndRepository.findAll().size();

        // Update the dnd
        Dnd updatedDnd = dndRepository.findById(dnd.getId()).get();
        updatedDnd
            .joinAt(UPDATED_JOIN_AT)
            .joinChannel(UPDATED_JOIN_CHANNEL);
        DndDTO dndDTO = dndMapper.toDto(updatedDnd);

        restDndMockMvc.perform(put("/api/dnds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dndDTO)))
            .andExpect(status().isOk());

        // Validate the Dnd in the database
        List<Dnd> dndList = dndRepository.findAll();
        assertThat(dndList).hasSize(databaseSizeBeforeUpdate);
        Dnd testDnd = dndList.get(dndList.size() - 1);
        assertThat(testDnd.getJoinAt()).isEqualTo(UPDATED_JOIN_AT);
        assertThat(testDnd.getJoinChannel()).isEqualTo(UPDATED_JOIN_CHANNEL);
    }

    @Test
    public void updateNonExistingDnd() throws Exception {
        int databaseSizeBeforeUpdate = dndRepository.findAll().size();

        // Create the Dnd
        DndDTO dndDTO = dndMapper.toDto(dnd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restDndMockMvc.perform(put("/api/dnds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dndDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dnd in the database
        List<Dnd> dndList = dndRepository.findAll();
        assertThat(dndList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDnd() throws Exception {
        // Initialize the database
        dndRepository.save(dnd);

        int databaseSizeBeforeDelete = dndRepository.findAll().size();

        // Get the dnd
        restDndMockMvc.perform(delete("/api/dnds/{id}", dnd.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dnd> dndList = dndRepository.findAll();
        assertThat(dndList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dnd.class);
        Dnd dnd1 = new Dnd();
        dnd1.setId("id1");
        Dnd dnd2 = new Dnd();
        dnd2.setId(dnd1.getId());
        assertThat(dnd1).isEqualTo(dnd2);
        dnd2.setId("id2");
        assertThat(dnd1).isNotEqualTo(dnd2);
        dnd1.setId(null);
        assertThat(dnd1).isNotEqualTo(dnd2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DndDTO.class);
        DndDTO dndDTO1 = new DndDTO();
        dndDTO1.setId("id1");
        DndDTO dndDTO2 = new DndDTO();
        assertThat(dndDTO1).isNotEqualTo(dndDTO2);
        dndDTO2.setId(dndDTO1.getId());
        assertThat(dndDTO1).isEqualTo(dndDTO2);
        dndDTO2.setId("id2");
        assertThat(dndDTO1).isNotEqualTo(dndDTO2);
        dndDTO1.setId(null);
        assertThat(dndDTO1).isNotEqualTo(dndDTO2);
    }
}
