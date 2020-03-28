package com.dxj.module.system.domain.mapper;

import com.dxj.base.BaseMapper;
import com.dxj.module.system.domain.entity.Role;
import com.dxj.module.system.domain.dto.RoleSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Sinkiang
 * @date 2019-5-23
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleSmallMapper extends BaseMapper<RoleSmallDTO, Role> {

}
