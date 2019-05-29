package com.dxj.service.mapper;

import com.dxj.domain.Log;
import com.dxj.mapper.EntityMapper;
import com.dxj.service.dto.LogErrorDTO;
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
