package com.ft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ft.service.SmsService;
import com.ft.web.rest.errors.BadRequestAlertException;
import com.ft.web.rest.util.HeaderUtil;
import com.ft.web.rest.util.PaginationUtil;
import com.ft.service.dto.SmsDTO;
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
 * REST controller for managing Sms.
 */
@RestController
@RequestMapping("/api")
public class SmsResource {

    private final Logger log = LoggerFactory.getLogger(SmsResource.class);

    private static final String ENTITY_NAME = "sms";

    private final SmsService smsService;

    public SmsResource(SmsService smsService) {
        this.smsService = smsService;
    }

    /**
     * POST  /sms : Create a new sms.
     *
     * @param smsDTO the smsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new smsDTO, or with status 400 (Bad Request) if the sms has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sms")
    @Timed
    public ResponseEntity<SmsDTO> createSms(@Valid @RequestBody SmsDTO smsDTO) throws URISyntaxException {
        log.debug("REST request to save Sms : {}", smsDTO);
        if (smsDTO.getId() != null) {
            throw new BadRequestAlertException("A new sms cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SmsDTO result = smsService.save(smsDTO);
        return ResponseEntity.created(new URI("/api/sms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sms : Updates an existing sms.
     *
     * @param smsDTO the smsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated smsDTO,
     * or with status 400 (Bad Request) if the smsDTO is not valid,
     * or with status 500 (Internal Server Error) if the smsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sms")
    @Timed
    public ResponseEntity<SmsDTO> updateSms(@Valid @RequestBody SmsDTO smsDTO) throws URISyntaxException {
        log.debug("REST request to update Sms : {}", smsDTO);
        if (smsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SmsDTO result = smsService.save(smsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, smsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sms : get all the sms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sms in body
     */
    @GetMapping("/sms")
    @Timed
    public ResponseEntity<List<SmsDTO>> getAllSms(Pageable pageable) {
        log.debug("REST request to get a page of Sms");
        Page<SmsDTO> page = smsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sms/:id : get the "id" sms.
     *
     * @param id the id of the smsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the smsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sms/{id}")
    @Timed
    public ResponseEntity<SmsDTO> getSms(@PathVariable String id) {
        log.debug("REST request to get Sms : {}", id);
        Optional<SmsDTO> smsDTO = smsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(smsDTO);
    }

    /**
     * DELETE  /sms/:id : delete the "id" sms.
     *
     * @param id the id of the smsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sms/{id}")
    @Timed
    public ResponseEntity<Void> deleteSms(@PathVariable String id) {
        log.debug("REST request to delete Sms : {}", id);
        smsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
