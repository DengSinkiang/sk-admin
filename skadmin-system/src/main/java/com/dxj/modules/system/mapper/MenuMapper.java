package com.dxj.modules.system.mapper;

import com.dxj.modules.system.domain.Menu;
import com.dxj.mapper.EntityMapper;
import com.dxj.modules.system.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

/**
 * @author dxj
 * @date 2018-12-17
 */
@Service
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

}
