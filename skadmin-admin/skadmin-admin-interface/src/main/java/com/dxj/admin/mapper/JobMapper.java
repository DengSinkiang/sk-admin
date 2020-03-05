package com.dxj.admin.mapper;

import com.dxj.admin.domain.entity.Job;
import com.dxj.admin.domain.dto.JobDTO;
import com.dxj.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

/**
* @author dxj
* @date 2019-03-29
*/
@Service
@Mapper(componentModel = "spring", uses = {DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends EntityMapper<JobDTO, Job> {
    @Mapping(source = "deptSuperiorName", target = "deptSuperiorName")
    JobDTO toDto(Job job, String deptSuperiorName);
}
