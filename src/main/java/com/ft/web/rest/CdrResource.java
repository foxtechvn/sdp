package com.ft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ft.service.CdrService;
import com.ft.web.rest.errors.BadRequestAlertException;
import com.ft.web.rest.util.HeaderUtil;
import com.ft.web.rest.util.PaginationUtil;
import com.ft.service.dto.CdrDTO;
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
 * REST controller for managing Cdr.
 */
@RestController
@RequestMapping("/api")
public class CdrResource {

    private final Logger log = LoggerFactory.getLogger(CdrResource.class);

    private static final String ENTITY_NAME = "cdr";

    private final CdrService cdrService;

    public CdrResource(CdrService cdrService) {
        this.cdrService = cdrService;
    }

    /**
     * POST  /cdrs : Create a new cdr.
     *
     * @param cdrDTO the cdrDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cdrDTO, or with status 400 (Bad Request) if the cdr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cdrs")
    @Timed
    public ResponseEntity<CdrDTO> createCdr(@Valid @RequestBody CdrDTO cdrDTO) throws URISyntaxException {
        log.debug("REST request to save Cdr : {}", cdrDTO);
        if (cdrDTO.getId() != null) {
            throw new BadRequestAlertException("A new cdr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CdrDTO result = cdrService.save(cdrDTO);
        return ResponseEntity.created(new URI("/api/cdrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cdrs : Updates an existing cdr.
     *
     * @param cdrDTO the cdrDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cdrDTO,
     * or with status 400 (Bad Request) if the cdrDTO is not valid,
     * or with status 500 (Internal Server Error) if the cdrDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cdrs")
    @Timed
    public ResponseEntity<CdrDTO> updateCdr(@Valid @RequestBody CdrDTO cdrDTO) throws URISyntaxException {
        log.debug("REST request to update Cdr : {}", cdrDTO);
        if (cdrDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CdrDTO result = cdrService.save(cdrDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cdrDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cdrs : get all the cdrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cdrs in body
     */
    @GetMapping("/cdrs")
    @Timed
    public ResponseEntity<List<CdrDTO>> getAllCdrs(Pageable pageable) {
        log.debug("REST request to get a page of Cdrs");
        Page<CdrDTO> page = cdrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cdrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cdrs/:id : get the "id" cdr.
     *
     * @param id the id of the cdrDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cdrDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cdrs/{id}")
    @Timed
    public ResponseEntity<CdrDTO> getCdr(@PathVariable String id) {
        log.debug("REST request to get Cdr : {}", id);
        Optional<CdrDTO> cdrDTO = cdrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cdrDTO);
    }

    /**
     * DELETE  /cdrs/:id : delete the "id" cdr.
     *
     * @param id the id of the cdrDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cdrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCdr(@PathVariable String id) {
        log.debug("REST request to delete Cdr : {}", id);
        cdrService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
