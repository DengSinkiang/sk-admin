package com.dxj.module.system.domain.mapstruct;

import com.dxj.module.system.domain.dto.DictDetailDTO;
import com.dxj.module.system.domain.entity.DictDetail;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-16T19:44:22+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
@Component
public class DictDetailMapperImpl implements DictDetailMapper {

    @Autowired
    private DictSmallMapper dictSmallMapper;

    @Override
    public DictDetail toEntity(DictDetailDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DictDetail dictDetail = new DictDetail();

        dictDetail.setCreateBy( dto.getCreateBy() );
        dictDetail.setUpdatedBy( dto.getUpdatedBy() );
        dictDetail.setCreateTime( dto.getCreateTime() );
        dictDetail.setUpdateTime( dto.getUpdateTime() );
        dictDetail.setId( dto.getId() );
        dictDetail.setDict( dictSmallMapper.toEntity( dto.getDict() ) );
        dictDetail.setLabel( dto.getLabel() );
        dictDetail.setValue( dto.getValue() );
        dictDetail.setDictSort( dto.getDictSort() );

        return dictDetail;
    }

    @Override
    public DictDetailDTO toDto(DictDetail entity) {
        if ( entity == null ) {
            return null;
        }

        DictDetailDTO dictDetailDTO = new DictDetailDTO();

        dictDetailDTO.setCreateBy( entity.getCreateBy() );
        dictDetailDTO.setUpdatedBy( entity.getUpdatedBy() );
        dictDetailDTO.setCreateTime( entity.getCreateTime() );
        dictDetailDTO.setUpdateTime( entity.getUpdateTime() );
        dictDetailDTO.setId( entity.getId() );
        dictDetailDTO.setDict( dictSmallMapper.toDto( entity.getDict() ) );
        dictDetailDTO.setLabel( entity.getLabel() );
        dictDetailDTO.setValue( entity.getValue() );
        dictDetailDTO.setDictSort( entity.getDictSort() );

        return dictDetailDTO;
    }

    @Override
    public List<DictDetail> toEntity(List<DictDetailDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DictDetail> list = new ArrayList<DictDetail>( dtoList.size() );
        for ( DictDetailDTO dictDetailDTO : dtoList ) {
            list.add( toEntity( dictDetailDTO ) );
        }

        return list;
    }

    @Override
    public List<DictDetailDTO> toDto(List<DictDetail> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DictDetailDTO> list = new ArrayList<DictDetailDTO>( entityList.size() );
        for ( DictDetail dictDetail : entityList ) {
            list.add( toDto( dictDetail ) );
        }

        return list;
    }
}
