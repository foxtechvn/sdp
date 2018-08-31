package com.ft.service;

import com.ft.domain.SubProduct;
import com.ft.repository.SubProductRepository;
import com.ft.service.dto.SubProductDTO;
import com.ft.service.mapper.SubProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;
/**
 * Service Implementation for managing SubProduct.
 */
@Service
public class SubProductService {

    private final Logger log = LoggerFactory.getLogger(SubProductService.class);

    private final SubProductRepository subProductRepository;

    private final SubProductMapper subProductMapper;

    public SubProductService(SubProductRepository subProductRepository, SubProductMapper subProductMapper) {
        this.subProductRepository = subProductRepository;
        this.subProductMapper = subProductMapper;
    }

    /**
     * Save a subProduct.
     *
     * @param subProductDTO the entity to save
     * @return the persisted entity
     */
    public SubProductDTO save(SubProductDTO subProductDTO) {
        log.debug("Request to save SubProduct : {}", subProductDTO);
        SubProduct subProduct = subProductMapper.toEntity(subProductDTO);
        subProduct = subProductRepository.save(subProduct);
        return subProductMapper.toDto(subProduct);
    }

    /**
     * Get all the subProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<SubProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubProducts");
        return subProductRepository.findAll(pageable)
            .map(subProductMapper::toDto);
    }


    /**
     * Get one subProduct by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<SubProductDTO> findOne(String id) {
        log.debug("Request to get SubProduct : {}", id);
        return subProductRepository.findById(id)
            .map(subProductMapper::toDto);
    }

    /**
     * Delete the subProduct by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete SubProduct : {}", id);
        subProductRepository.deleteById(id);
    }
}
