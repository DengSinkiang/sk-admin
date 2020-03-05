package com.dxj.admin.mapper;

import com.dxj.admin.domain.entity.Permission;
import com.dxj.admin.domain.dto.PermissionDTO;
import com.dxj.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

/**
 * @author dxj
 * @date 2019-03-23
 */
@Service
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {

}
