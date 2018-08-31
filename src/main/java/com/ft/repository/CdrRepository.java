package com.ft.repository;

import com.ft.domain.Cdr;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Cdr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CdrRepository extends MongoRepository<Cdr, String> {

}
