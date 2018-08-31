package com.ft.repository;

import com.ft.domain.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Subscriber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscriberRepository extends MongoRepository<Subscriber, String> {

}
