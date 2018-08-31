package com.ft.web.rest;

import com.ft.SdpApp;

import com.ft.domain.SubProduct;
import com.ft.repository.SubProductRepository;
import com.ft.service.SubProductService;
import com.ft.service.dto.SubProductDTO;
import com.ft.service.mapper.SubProductMapper;
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
import org.springframework.util.Base64Utils;

import java.util.List;


import static com.ft.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SubProductResource REST controller.
 *
 * @see SubProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SdpApp.class)
public class SubProductResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MISC = "AAAAAAAAAA";
    private static final String UPDATED_MISC = "BBBBBBBBBB";

    @Autowired
    private SubProductRepository subProductRepository;


    @Autowired
    private SubProductMapper subProductMapper;
    

    @Autowired
    private SubProductService subProductService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSubProductMockMvc;

    private SubProduct subProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubProductResource subProductResource = new SubProductResource(subProductService);
        this.restSubProductMockMvc = MockMvcBuilders.standaloneSetup(subProductResource)
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
    public static SubProduct createEntity() {
        SubProduct subProduct = new SubProduct()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .misc(DEFAULT_MISC);
        return subProduct;
    }

    @Before
    public void initTest() {
        subProductRepository.deleteAll();
        subProduct = createEntity();
    }

    @Test
    public void createSubProduct() throws Exception {
        int databaseSizeBeforeCreate = subProductRepository.findAll().size();

        // Create the SubProduct
        SubProductDTO subProductDTO = subProductMapper.toDto(subProduct);
        restSubProductMockMvc.perform(post("/api/sub-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subProductDTO)))
            .andExpect(status().isCreated());

        // Validate the SubProduct in the database
        List<SubProduct> subProductList = subProductRepository.findAll();
        assertThat(subProductList).hasSize(databaseSizeBeforeCreate + 1);
        SubProduct testSubProduct = subProductList.get(subProductList.size() - 1);
        assertThat(testSubProduct.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSubProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSubProduct.getMisc()).isEqualTo(DEFAULT_MISC);
    }

    @Test
    public void createSubProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subProductRepository.findAll().size();

        // Create the SubProduct with an existing ID
        subProduct.setId("existing_id");
        SubProductDTO subProductDTO = subProductMapper.toDto(subProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubProductMockMvc.perform(post("/api/sub-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubProduct in the database
        List<SubProduct> subProductList = subProductRepository.findAll();
        assertThat(subProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = subProductRepository.findAll().size();
        // set the field null
        subProduct.setCode(null);

        // Create the SubProduct, which fails.
        SubProductDTO subProductDTO = subProductMapper.toDto(subProduct);

        restSubProductMockMvc.perform(post("/api/sub-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subProductDTO)))
            .andExpect(status().isBadRequest());

        List<SubProduct> subProductList = subProductRepository.findAll();
        assertThat(subProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSubProducts() throws Exception {
        // Initialize the database
        subProductRepository.save(subProduct);

        // Get all the subProductList
        restSubProductMockMvc.perform(get("/api/sub-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subProduct.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].misc").value(hasItem(DEFAULT_MISC.toString())));
    }
    

    @Test
    public void getSubProduct() throws Exception {
        // Initialize the database
        subProductRepository.save(subProduct);

        // Get the subProduct
        restSubProductMockMvc.perform(get("/api/sub-products/{id}", subProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subProduct.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.misc").value(DEFAULT_MISC.toString()));
    }
    @Test
    public void getNonExistingSubProduct() throws Exception {
        // Get the subProduct
        restSubProductMockMvc.perform(get("/api/sub-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSubProduct() throws Exception {
        // Initialize the database
        subProductRepository.save(subProduct);

        int databaseSizeBeforeUpdate = subProductRepository.findAll().size();

        // Update the subProduct
        SubProduct updatedSubProduct = subProductRepository.findById(subProduct.getId()).get();
        updatedSubProduct
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .misc(UPDATED_MISC);
        SubProductDTO subProductDTO = subProductMapper.toDto(updatedSubProduct);

        restSubProductMockMvc.perform(put("/api/sub-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subProductDTO)))
            .andExpect(status().isOk());

        // Validate the SubProduct in the database
        List<SubProduct> subProductList = subProductRepository.findAll();
        assertThat(subProductList).hasSize(databaseSizeBeforeUpdate);
        SubProduct testSubProduct = subProductList.get(subProductList.size() - 1);
        assertThat(testSubProduct.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSubProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSubProduct.getMisc()).isEqualTo(UPDATED_MISC);
    }

    @Test
    public void updateNonExistingSubProduct() throws Exception {
        int databaseSizeBeforeUpdate = subProductRepository.findAll().size();

        // Create the SubProduct
        SubProductDTO subProductDTO = subProductMapper.toDto(subProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restSubProductMockMvc.perform(put("/api/sub-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubProduct in the database
        List<SubProduct> subProductList = subProductRepository.findAll();
        assertThat(subProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSubProduct() throws Exception {
        // Initialize the database
        subProductRepository.save(subProduct);

        int databaseSizeBeforeDelete = subProductRepository.findAll().size();

        // Get the subProduct
        restSubProductMockMvc.perform(delete("/api/sub-products/{id}", subProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SubProduct> subProductList = subProductRepository.findAll();
        assertThat(subProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubProduct.class);
        SubProduct subProduct1 = new SubProduct();
        subProduct1.setId("id1");
        SubProduct subProduct2 = new SubProduct();
        subProduct2.setId(subProduct1.getId());
        assertThat(subProduct1).isEqualTo(subProduct2);
        subProduct2.setId("id2");
        assertThat(subProduct1).isNotEqualTo(subProduct2);
        subProduct1.setId(null);
        assertThat(subProduct1).isNotEqualTo(subProduct2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubProductDTO.class);
        SubProductDTO subProductDTO1 = new SubProductDTO();
        subProductDTO1.setId("id1");
        SubProductDTO subProductDTO2 = new SubProductDTO();
        assertThat(subProductDTO1).isNotEqualTo(subProductDTO2);
        subProductDTO2.setId(subProductDTO1.getId());
        assertThat(subProductDTO1).isEqualTo(subProductDTO2);
        subProductDTO2.setId("id2");
        assertThat(subProductDTO1).isNotEqualTo(subProductDTO2);
        subProductDTO1.setId(null);
        assertThat(subProductDTO1).isNotEqualTo(subProductDTO2);
    }
}
