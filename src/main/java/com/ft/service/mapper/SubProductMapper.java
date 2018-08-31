package com.ft.service.mapper;

import com.ft.domain.*;
import com.ft.service.dto.SubProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SubProduct and its DTO SubProductDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubProductMapper extends EntityMapper<SubProductDTO, SubProduct> {


}
