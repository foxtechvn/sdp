package com.ft.service;

import com.ft.domain.Dnd;
import com.ft.repository.DndRepository;
import com.ft.service.dto.DndDTO;
import com.ft.service.mapper.DndMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;
/**
 * Service Implementation for managing Dnd.
 */
@Service
public class DndService {

    private final Logger log = LoggerFactory.getLogger(DndService.class);

    private final DndRepository dndRepository;

    private final DndMapper dndMapper;

    public DndService(DndRepository dndRepository, DndMapper dndMapper) {
        this.dndRepository = dndRepository;
        this.dndMapper = dndMapper;
    }

    /**
     * Save a dnd.
     *
     * @param dndDTO the entity to save
     * @return the persisted entity
     */
    public DndDTO save(DndDTO dndDTO) {
        log.debug("Request to save Dnd : {}", dndDTO);
        Dnd dnd = dndMapper.toEntity(dndDTO);
        dnd = dndRepository.save(dnd);
        return dndMapper.toDto(dnd);
    }

    /**
     * Get all the dnds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<DndDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dnds");
        return dndRepository.findAll(pageable)
            .map(dndMapper::toDto);
    }


    /**
     * Get one dnd by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<DndDTO> findOne(String id) {
        log.debug("Request to get Dnd : {}", id);
        return dndRepository.findById(id)
            .map(dndMapper::toDto);
    }

    /**
     * Delete the dnd by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Dnd : {}", id);
        dndRepository.deleteById(id);
    }
}
