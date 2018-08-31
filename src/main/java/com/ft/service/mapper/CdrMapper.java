package com.ft.service.mapper;

import com.ft.domain.*;
import com.ft.service.dto.CdrDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cdr and its DTO CdrDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CdrMapper extends EntityMapper<CdrDTO, Cdr> {


}
