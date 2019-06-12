package com.dxj.log.mapper;

import com.dxj.common.mapper.EntityMapper;
import com.dxj.log.domain.Log;
import com.dxj.log.dto.LogErrorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

/**
 * @Author: dxj
 * @Date: 2019-05-27 09:46
 */
@Service
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogErrorMapper extends EntityMapper<LogErrorDTO, Log> {

}
