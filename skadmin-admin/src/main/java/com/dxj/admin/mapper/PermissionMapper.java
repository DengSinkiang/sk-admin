package com.dxj.admin.mapper;

import com.dxj.admin.domain.Permission;
import com.dxj.common.mapper.EntityMapper;
import com.dxj.admin.dto.PermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

/**
 * @author dxj
 * @date 2018-11-23
 */
@Service
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {

}
