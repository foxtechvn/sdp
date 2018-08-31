package com.ft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ft.service.DndService;
import com.ft.web.rest.errors.BadRequestAlertException;
import com.ft.web.rest.util.HeaderUtil;
import com.ft.web.rest.util.PaginationUtil;
import com.ft.service.dto.DndDTO;
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
 * REST controller for managing Dnd.
 */
@RestController
@RequestMapping("/api")
public class DndResource {

    private final Logger log = LoggerFactory.getLogger(DndResource.class);

    private static final String ENTITY_NAME = "dnd";

    private final DndService dndService;

    public DndResource(DndService dndService) {
        this.dndService = dndService;
    }

    /**
     * POST  /dnds : Create a new dnd.
     *
     * @param dndDTO the dndDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dndDTO, or with status 400 (Bad Request) if the dnd has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dnds")
    @Timed
    public ResponseEntity<DndDTO> createDnd(@Valid @RequestBody DndDTO dndDTO) throws URISyntaxException {
        log.debug("REST request to save Dnd : {}", dndDTO);
        if (dndDTO.getId() != null) {
            throw new BadRequestAlertException("A new dnd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DndDTO result = dndService.save(dndDTO);
        return ResponseEntity.created(new URI("/api/dnds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dnds : Updates an existing dnd.
     *
     * @param dndDTO the dndDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dndDTO,
     * or with status 400 (Bad Request) if the dndDTO is not valid,
     * or with status 500 (Internal Server Error) if the dndDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dnds")
    @Timed
    public ResponseEntity<DndDTO> updateDnd(@Valid @RequestBody DndDTO dndDTO) throws URISyntaxException {
        log.debug("REST request to update Dnd : {}", dndDTO);
        if (dndDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DndDTO result = dndService.save(dndDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dndDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dnds : get all the dnds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dnds in body
     */
    @GetMapping("/dnds")
    @Timed
    public ResponseEntity<List<DndDTO>> getAllDnds(Pageable pageable) {
        log.debug("REST request to get a page of Dnds");
        Page<DndDTO> page = dndService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dnds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dnds/:id : get the "id" dnd.
     *
     * @param id the id of the dndDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dndDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dnds/{id}")
    @Timed
    public ResponseEntity<DndDTO> getDnd(@PathVariable String id) {
        log.debug("REST request to get Dnd : {}", id);
        Optional<DndDTO> dndDTO = dndService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dndDTO);
    }

    /**
     * DELETE  /dnds/:id : delete the "id" dnd.
     *
     * @param id the id of the dndDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dnds/{id}")
    @Timed
    public ResponseEntity<Void> deleteDnd(@PathVariable String id) {
        log.debug("REST request to delete Dnd : {}", id);
        dndService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
