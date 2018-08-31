package com.ft.repository;

import com.ft.domain.SubProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SubProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubProductRepository extends MongoRepository<SubProduct, String> {

}
