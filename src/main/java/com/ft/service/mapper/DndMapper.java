package com.ft.service.mapper;

import com.ft.domain.*;
import com.ft.service.dto.DndDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dnd and its DTO DndDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DndMapper extends EntityMapper<DndDTO, Dnd> {


}
