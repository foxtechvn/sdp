package com.ft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ft.service.SubProductService;
import com.ft.web.rest.errors.BadRequestAlertException;
import com.ft.web.rest.util.HeaderUtil;
import com.ft.web.rest.util.PaginationUtil;
import com.ft.service.dto.SubProductDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SubProduct.
 */
@RestController
@RequestMapping("/api")
public class SubProductResource {

    private final Logger log = LoggerFactory.getLogger(SubProductResource.class);

    private static final String ENTITY_NAME = "subProduct";

    private final SubProductService subProductService;

    public SubProductResource(SubProductService subProductService) {
        this.subProductService = subProductService;
    }

    /**
     * POST  /sub-products : Create a new subProduct.
     *
     * @param subProductDTO the subProductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subProductDTO, or with status 400 (Bad Request) if the subProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sub-products")
    @Timed
    public ResponseEntity<SubProductDTO> createSubProduct(@Valid @RequestBody SubProductDTO subProductDTO) throws URISyntaxException {
        log.debug("REST request to save SubProduct : {}", subProductDTO);
        if (subProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new subProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubProductDTO result = subProductService.save(subProductDTO);
        return ResponseEntity.created(new URI("/api/sub-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sub-products : Updates an existing subProduct.
     *
     * @param subProductDTO the subProductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subProductDTO,
     * or with status 400 (Bad Request) if the subProductDTO is not valid,
     * or with status 500 (Internal Server Error) if the subProductDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sub-products")
    @Timed
    public ResponseEntity<SubProductDTO> updateSubProduct(@Valid @RequestBody SubProductDTO subProductDTO) throws URISyntaxException {
        log.debug("REST request to update SubProduct : {}", subProductDTO);
        if (subProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubProductDTO result = subProductService.save(subProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sub-products : get all the subProducts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subProducts in body
     */
    @GetMapping("/sub-products")
    @Timed
    public ResponseEntity<List<SubProductDTO>> getAllSubProducts(Pageable pageable) {
        log.debug("REST request to get a page of SubProducts");
        Page<SubProductDTO> page = subProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sub-products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sub-products/:id : get the "id" subProduct.
     *
     * @param id the id of the subProductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subProductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sub-products/{id}")
    @Timed
    public ResponseEntity<SubProductDTO> getSubProduct(@PathVariable String id) {
        log.debug("REST request to get SubProduct : {}", id);
        Optional<SubProductDTO> subProductDTO = subProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subProductDTO);
    }

    /**
     * DELETE  /sub-products/:id : delete the "id" subProduct.
     *
     * @param id the id of the subProductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sub-products/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubProduct(@PathVariable String id) {
        log.debug("REST request to delete SubProduct : {}", id);
        subProductService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
