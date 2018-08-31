package com.ft.service.mapper;

import com.ft.domain.*;
import com.ft.service.dto.SmsContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SmsContent and its DTO SmsContentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SmsContentMapper extends EntityMapper<SmsContentDTO, SmsContent> {


}
