package com.dxj.admin.mapper;

import com.dxj.common.mapper.EntityMapper;
import com.dxj.admin.domain.Role;
import com.dxj.admin.dto.RoleSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @Author: dxj
 * @Date: 2019-05-27 11:21
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleSmallMapper extends EntityMapper<RoleSmallDTO, Role> {

}
