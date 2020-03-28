package com.dxj.module.system.domain.mapper;

import com.dxj.base.BaseMapper;
import com.dxj.module.system.domain.entity.Dept;
import com.dxj.module.system.domain.dto.DeptSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Sinkiang
* @date 2019-03-25
*/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptSmallMapper extends BaseMapper<DeptSmallDTO, Dept> {

}
