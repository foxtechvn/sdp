package com.ft.service;

import com.ft.domain.Cdr;
import com.ft.repository.CdrRepository;
import com.ft.service.dto.CdrDTO;
import com.ft.service.mapper.CdrMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;
/**
 * Service Implementation for managing Cdr.
 */
@Service
public class CdrService {

    private final Logger log = LoggerFactory.getLogger(CdrService.class);

    private final CdrRepository cdrRepository;

    private final CdrMapper cdrMapper;

    public CdrService(CdrRepository cdrRepository, CdrMapper cdrMapper) {
        this.cdrRepository = cdrRepository;
        this.cdrMapper = cdrMapper;
    }

    /**
     * Save a cdr.
     *
     * @param cdrDTO the entity to save
     * @return the persisted entity
     */
    public CdrDTO save(CdrDTO cdrDTO) {
        log.debug("Request to save Cdr : {}", cdrDTO);
        Cdr cdr = cdrMapper.toEntity(cdrDTO);
        cdr = cdrRepository.save(cdr);
        return cdrMapper.toDto(cdr);
    }

    /**
     * Get all the cdrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<CdrDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cdrs");
        return cdrRepository.findAll(pageable)
            .map(cdrMapper::toDto);
    }


    /**
     * Get one cdr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<CdrDTO> findOne(String id) {
        log.debug("Request to get Cdr : {}", id);
        return cdrRepository.findById(id)
            .map(cdrMapper::toDto);
    }

    /**
     * Delete the cdr by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Cdr : {}", id);
        cdrRepository.deleteById(id);
    }
}
