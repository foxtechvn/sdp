package com.ft.repository;

import com.ft.domain.SmsContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SmsContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SmsContentRepository extends MongoRepository<SmsContent, String> {

}
