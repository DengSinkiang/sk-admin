package com.dxj.admin.mapper;

import com.dxj.admin.domain.entity.DictDetail;
import com.dxj.admin.domain.dto.DictDetailDTO;
import com.dxj.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

/**
* @author dxj
* @date 2019-04-10
*/
@Service
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictDetailMapper extends EntityMapper<DictDetailDTO, DictDetail> {

}
