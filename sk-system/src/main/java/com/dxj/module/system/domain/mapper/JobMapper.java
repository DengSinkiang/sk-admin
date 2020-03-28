package com.dxj.module.system.domain.mapper;

import com.dxj.base.BaseMapper;
import com.dxj.module.system.domain.entity.Job;
import com.dxj.module.system.domain.dto.JobDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
* @author Sinkiang
* @date 2019-03-29
*/
@Mapper(componentModel = "spring",uses = {DeptMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends BaseMapper<JobDTO, Job> {

    /**
     * 转Dto
     * @param job 原始数据
     * @param deptSuperiorName /
     * @return /
     */
    @Mapping(source = "deptSuperiorName", target = "deptSuperiorName")
    JobDTO toDto(Job job, String deptSuperiorName);
}
