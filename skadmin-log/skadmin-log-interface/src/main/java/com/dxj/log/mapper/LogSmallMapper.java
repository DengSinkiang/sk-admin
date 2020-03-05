package com.dxj.log.mapper;

import com.dxj.common.mapper.EntityMapper;
import com.dxj.log.domain.entity.Log;
import com.dxj.log.domain.dto.LogSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

/**
 * @Author: dxj
 * @Date: 2019-05-27 10:56
 */
@Service
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogSmallMapper extends EntityMapper<LogSmallDTO, Log> {

}

