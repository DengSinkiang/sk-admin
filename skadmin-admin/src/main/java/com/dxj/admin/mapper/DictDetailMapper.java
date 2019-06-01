package com.dxj.admin.mapper;

import com.dxj.common.mapper.EntityMapper;
import com.dxj.admin.domain.DictDetail;
import com.dxj.admin.dto.DictDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

/**
* @author dxj
* @date 2019-04-10
*/
@Service
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictDetailMapper extends EntityMapper<DictDetailDTO, DictDetail> {

}
