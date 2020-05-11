package com.dxj.module.system.domain.mapstruct;

import com.dxj.base.BaseMapper;
import com.dxj.module.system.domain.entity.DictDetail;
import com.dxj.module.system.domain.dto.DictDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Sinkiang
* @date 2019-04-10
*/
@Mapper(componentModel = "spring", uses = {DictSmallMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictDetailMapper extends BaseMapper<DictDetailDTO, DictDetail> {

}
