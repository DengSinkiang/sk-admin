package com.dxj.modules.system.mapper;

import com.dxj.mapper.EntityMapper;
import com.dxj.modules.system.domain.Job;
import com.dxj.modules.system.dto.JobDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

/**
* @author dxj
* @date 2019-03-29
*/
@Service
@Mapper(componentModel = "spring", uses = {DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends EntityMapper<JobDTO, Job> {

}
