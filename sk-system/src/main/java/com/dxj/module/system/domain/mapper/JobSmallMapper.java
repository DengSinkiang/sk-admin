package com.dxj.module.system.domain.mapper;

import com.dxj.base.BaseMapper;
import com.dxj.module.system.domain.entity.Job;
import com.dxj.module.system.domain.dto.JobSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Sinkiang
* @date 2019-03-29
*/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobSmallMapper extends BaseMapper<JobSmallDTO, Job> {

}
