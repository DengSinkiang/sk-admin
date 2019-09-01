package com.dxj.admin.module.mapper;

import com.dxj.common.mapper.EntityMapper;
import com.dxj.admin.module.domain.TbUser;
import com.dxj.admin.module.dto.TbUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author sinkiang
* @date 2019-09-01
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TbUserMapper extends EntityMapper<TbUserDTO, TbUser> {

}
