package com.ft.repository;

import com.ft.domain.Dnd;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Dnd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DndRepository extends MongoRepository<Dnd, String> {

}
