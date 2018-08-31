package com.ft.service;

import com.ft.domain.SmsContent;
import com.ft.repository.SmsContentRepository;
import com.ft.service.dto.SmsContentDTO;
import com.ft.service.mapper.SmsContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;
/**
 * Service Implementation for managing SmsContent.
 */
@Service
public class SmsContentService {

    private final Logger log = LoggerFactory.getLogger(SmsContentService.class);

    private final SmsContentRepository smsContentRepository;

    private final SmsContentMapper smsContentMapper;

    public SmsContentService(SmsContentRepository smsContentRepository, SmsContentMapper smsContentMapper) {
        this.smsContentRepository = smsContentRepository;
        this.smsContentMapper = smsContentMapper;
    }

    /**
     * Save a smsContent.
     *
     * @param smsContentDTO the entity to save
     * @return the persisted entity
     */
    public SmsContentDTO save(SmsContentDTO smsContentDTO) {
        log.debug("Request to save SmsContent : {}", smsContentDTO);
        SmsContent smsContent = smsContentMapper.toEntity(smsContentDTO);
        smsContent = smsContentRepository.save(smsContent);
        return smsContentMapper.toDto(smsContent);
    }

    /**
     * Get all the smsContents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<SmsContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SmsContents");
        return smsContentRepository.findAll(pageable)
            .map(smsContentMapper::toDto);
    }


    /**
     * Get one smsContent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<SmsContentDTO> findOne(String id) {
        log.debug("Request to get SmsContent : {}", id);
        return smsContentRepository.findById(id)
            .map(smsContentMapper::toDto);
    }

    /**
     * Delete the smsContent by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete SmsContent : {}", id);
        smsContentRepository.deleteById(id);
    }
}
