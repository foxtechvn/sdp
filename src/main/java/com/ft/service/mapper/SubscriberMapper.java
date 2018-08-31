package com.ft.service.mapper;

import com.ft.domain.*;
import com.ft.service.dto.SubscriberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Subscriber and its DTO SubscriberDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubscriberMapper extends EntityMapper<SubscriberDTO, Subscriber> {


}
