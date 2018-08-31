package com.ft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ft.service.SmsContentService;
import com.ft.web.rest.errors.BadRequestAlertException;
import com.ft.web.rest.util.HeaderUtil;
import com.ft.web.rest.util.PaginationUtil;
import com.ft.service.dto.SmsContentDTO;
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
 * REST controller for managing SmsContent.
 */
@RestController
@RequestMapping("/api")
public class SmsContentResource {

    private final Logger log = LoggerFactory.getLogger(SmsContentResource.class);

    private static final String ENTITY_NAME = "smsContent";

    private final SmsContentService smsContentService;

    public SmsContentResource(SmsContentService smsContentService) {
        this.smsContentService = smsContentService;
    }

    /**
     * POST  /sms-contents : Create a new smsContent.
     *
     * @param smsContentDTO the smsContentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new smsContentDTO, or with status 400 (Bad Request) if the smsContent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sms-contents")
    @Timed
    public ResponseEntity<SmsContentDTO> createSmsContent(@Valid @RequestBody SmsContentDTO smsContentDTO) throws URISyntaxException {
        log.debug("REST request to save SmsContent : {}", smsContentDTO);
        if (smsContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new smsContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SmsContentDTO result = smsContentService.save(smsContentDTO);
        return ResponseEntity.created(new URI("/api/sms-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sms-contents : Updates an existing smsContent.
     *
     * @param smsContentDTO the smsContentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated smsContentDTO,
     * or with status 400 (Bad Request) if the smsContentDTO is not valid,
     * or with status 500 (Internal Server Error) if the smsContentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sms-contents")
    @Timed
    public ResponseEntity<SmsContentDTO> updateSmsContent(@Valid @RequestBody SmsContentDTO smsContentDTO) throws URISyntaxException {
        log.debug("REST request to update SmsContent : {}", smsContentDTO);
        if (smsContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SmsContentDTO result = smsContentService.save(smsContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, smsContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sms-contents : get all the smsContents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of smsContents in body
     */
    @GetMapping("/sms-contents")
    @Timed
    public ResponseEntity<List<SmsContentDTO>> getAllSmsContents(Pageable pageable) {
        log.debug("REST request to get a page of SmsContents");
        Page<SmsContentDTO> page = smsContentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sms-contents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sms-contents/:id : get the "id" smsContent.
     *
     * @param id the id of the smsContentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the smsContentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sms-contents/{id}")
    @Timed
    public ResponseEntity<SmsContentDTO> getSmsContent(@PathVariable String id) {
        log.debug("REST request to get SmsContent : {}", id);
        Optional<SmsContentDTO> smsContentDTO = smsContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(smsContentDTO);
    }

    /**
     * DELETE  /sms-contents/:id : delete the "id" smsContent.
     *
     * @param id the id of the smsContentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sms-contents/{id}")
    @Timed
    public ResponseEntity<Void> deleteSmsContent(@PathVariable String id) {
        log.debug("REST request to delete SmsContent : {}", id);
        smsContentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
