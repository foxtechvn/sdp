package com.ft.service;

import com.ft.domain.Subscriber;
import com.ft.repository.SubscriberRepository;
import com.ft.service.dto.SubscriberDTO;
import com.ft.service.mapper.SubscriberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;
/**
 * Service Implementation for managing Subscriber.
 */
@Service
public class SubscriberService {

    private final Logger log = LoggerFactory.getLogger(SubscriberService.class);

    private final SubscriberRepository subscriberRepository;

    private final SubscriberMapper subscriberMapper;

    public SubscriberService(SubscriberRepository subscriberRepository, SubscriberMapper subscriberMapper) {
        this.subscriberRepository = subscriberRepository;
        this.subscriberMapper = subscriberMapper;
    }

    /**
     * Save a subscriber.
     *
     * @param subscriberDTO the entity to save
     * @return the persisted entity
     */
    public SubscriberDTO save(SubscriberDTO subscriberDTO) {
        log.debug("Request to save Subscriber : {}", subscriberDTO);
        Subscriber subscriber = subscriberMapper.toEntity(subscriberDTO);
        subscriber = subscriberRepository.save(subscriber);
        return subscriberMapper.toDto(subscriber);
    }

    /**
     * Get all the subscribers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<SubscriberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Subscribers");
        return subscriberRepository.findAll(pageable)
            .map(subscriberMapper::toDto);
    }


    /**
     * Get one subscriber by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<SubscriberDTO> findOne(String id) {
        log.debug("Request to get Subscriber : {}", id);
        return subscriberRepository.findById(id)
            .map(subscriberMapper::toDto);
    }

    /**
     * Delete the subscriber by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Subscriber : {}", id);
        subscriberRepository.deleteById(id);
    }
}
