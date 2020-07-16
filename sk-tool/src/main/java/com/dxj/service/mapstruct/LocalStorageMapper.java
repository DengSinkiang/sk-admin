package com.dxj.service.mapstruct;

import com.dxj.base.BaseMapper;
import com.dxj.domain.entity.LocalStorage;
import com.dxj.domain.dto.LocalStorageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Sinkiang
 * @date 2019-09-05
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalStorageMapper extends BaseMapper<LocalStorageDTO, LocalStorage> {

}
