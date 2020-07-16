package com.dxj.domain.mapstruct;

import com.dxj.base.BaseMapper;
import com.dxj.domain.entity.Log;
import com.dxj.domain.dto.LogErrorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Sinkiang
 * @date 2019-5-22
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogErrorMapper extends BaseMapper<LogErrorDTO, Log> {

}
