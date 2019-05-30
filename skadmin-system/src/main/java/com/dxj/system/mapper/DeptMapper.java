package com.dxj.system.mapper;

import com.dxj.mapper.EntityMapper;
import com.dxj.system.domain.Dept;
import com.dxj.system.dto.DeptDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

/**
* @author dxj
* @date 2019-03-25
*/
@Service
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptMapper extends EntityMapper<DeptDTO, Dept> {

}
