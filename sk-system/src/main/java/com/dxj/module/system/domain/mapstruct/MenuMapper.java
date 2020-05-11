package com.dxj.module.system.domain.mapstruct;

import com.dxj.base.BaseMapper;
import com.dxj.module.system.domain.entity.Menu;
import com.dxj.module.system.domain.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Sinkiang
 * @date 2018-12-17
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends BaseMapper<MenuDTO, Menu> {

}
