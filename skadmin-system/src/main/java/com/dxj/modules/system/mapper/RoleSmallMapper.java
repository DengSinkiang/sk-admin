package com.dxj.modules.system.mapper;

import com.dxj.mapper.EntityMapper;
import com.dxj.modules.system.domain.Role;
import com.dxj.modules.system.dto.RoleSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @Author: dxj
 * @Date: 2019-05-27 11:21
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleSmallMapper extends EntityMapper<RoleSmallDTO, Role> {

}
