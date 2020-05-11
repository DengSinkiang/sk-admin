package com.dxj.module.system.domain.mapstruct;

import com.dxj.base.BaseMapper;
import com.dxj.module.system.domain.entity.Role;
import com.dxj.module.system.domain.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Sinkiang
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", uses = {MenuMapper.class, DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends BaseMapper<RoleDTO, Role> {

}
