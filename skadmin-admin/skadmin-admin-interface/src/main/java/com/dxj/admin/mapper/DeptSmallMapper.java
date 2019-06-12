package com.dxj.admin.mapper;

import com.dxj.common.mapper.EntityMapper;
import com.dxj.admin.domain.Dept;
import com.dxj.admin.dto.DeptSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author dxj
* @date 2019-03-25
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptSmallMapper extends EntityMapper<DeptSmallDTO, Dept> {

}
