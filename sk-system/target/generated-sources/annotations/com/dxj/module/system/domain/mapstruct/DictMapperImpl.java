package com.dxj.module.system.domain.mapstruct;

import com.dxj.module.system.domain.dto.DictDTO;
import com.dxj.module.system.domain.dto.DictDetailDTO;
import com.dxj.module.system.domain.dto.DictSmallDTO;
import com.dxj.module.system.domain.entity.Dict;
import com.dxj.module.system.domain.entity.DictDetail;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-16T19:44:22+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
@Component
public class DictMapperImpl implements DictMapper {

    @Override
    public Dict toEntity(DictDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Dict dict = new Dict();

        dict.setCreateBy( dto.getCreateBy() );
        dict.setUpdatedBy( dto.getUpdatedBy() );
        dict.setCreateTime( dto.getCreateTime() );
        dict.setUpdateTime( dto.getUpdateTime() );
        dict.setId( dto.getId() );
        dict.setDictDetails( dictDetailDTOListToDictDetailList( dto.getDictDetails() ) );
        dict.setName( dto.getName() );
        dict.setDescription( dto.getDescription() );

        return dict;
    }

    @Override
    public DictDTO toDto(Dict entity) {
        if ( entity == null ) {
            return null;
        }

        DictDTO dictDTO = new DictDTO();

        dictDTO.setCreateBy( entity.getCreateBy() );
        dictDTO.setUpdatedBy( entity.getUpdatedBy() );
        dictDTO.setCreateTime( entity.getCreateTime() );
        dictDTO.setUpdateTime( entity.getUpdateTime() );
        dictDTO.setId( entity.getId() );
        dictDTO.setDictDetails( dictDetailListToDictDetailDTOList( entity.getDictDetails() ) );
        dictDTO.setName( entity.getName() );
        dictDTO.setDescription( entity.getDescription() );

        return dictDTO;
    }

    @Override
    public List<Dict> toEntity(List<DictDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Dict> list = new ArrayList<Dict>( dtoList.size() );
        for ( DictDTO dictDTO : dtoList ) {
            list.add( toEntity( dictDTO ) );
        }

        return list;
    }

    @Override
    public List<DictDTO> toDto(List<Dict> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DictDTO> list = new ArrayList<DictDTO>( entityList.size() );
        for ( Dict dict : entityList ) {
            list.add( toDto( dict ) );
        }

        return list;
    }

    protected Dict dictSmallDTOToDict(DictSmallDTO dictSmallDTO) {
        if ( dictSmallDTO == null ) {
            return null;
        }

        Dict dict = new Dict();

        dict.setId( dictSmallDTO.getId() );

        return dict;
    }

    protected DictDetail dictDetailDTOToDictDetail(DictDetailDTO dictDetailDTO) {
        if ( dictDetailDTO == null ) {
            return null;
        }

        DictDetail dictDetail = new DictDetail();

        dictDetail.setCreateBy( dictDetailDTO.getCreateBy() );
        dictDetail.setUpdatedBy( dictDetailDTO.getUpdatedBy() );
        dictDetail.setCreateTime( dictDetailDTO.getCreateTime() );
        dictDetail.setUpdateTime( dictDetailDTO.getUpdateTime() );
        dictDetail.setId( dictDetailDTO.getId() );
        dictDetail.setDict( dictSmallDTOToDict( dictDetailDTO.getDict() ) );
        dictDetail.setLabel( dictDetailDTO.getLabel() );
        dictDetail.setValue( dictDetailDTO.getValue() );
        dictDetail.setDictSort( dictDetailDTO.getDictSort() );

        return dictDetail;
    }

    protected List<DictDetail> dictDetailDTOListToDictDetailList(List<DictDetailDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<DictDetail> list1 = new ArrayList<DictDetail>( list.size() );
        for ( DictDetailDTO dictDetailDTO : list ) {
            list1.add( dictDetailDTOToDictDetail( dictDetailDTO ) );
        }

        return list1;
    }

    protected DictSmallDTO dictToDictSmallDTO(Dict dict) {
        if ( dict == null ) {
            return null;
        }

        DictSmallDTO dictSmallDTO = new DictSmallDTO();

        dictSmallDTO.setId( dict.getId() );

        return dictSmallDTO;
    }

    protected DictDetailDTO dictDetailToDictDetailDTO(DictDetail dictDetail) {
        if ( dictDetail == null ) {
            return null;
        }

        DictDetailDTO dictDetailDTO = new DictDetailDTO();

        dictDetailDTO.setCreateBy( dictDetail.getCreateBy() );
        dictDetailDTO.setUpdatedBy( dictDetail.getUpdatedBy() );
        dictDetailDTO.setCreateTime( dictDetail.getCreateTime() );
        dictDetailDTO.setUpdateTime( dictDetail.getUpdateTime() );
        dictDetailDTO.setId( dictDetail.getId() );
        dictDetailDTO.setDict( dictToDictSmallDTO( dictDetail.getDict() ) );
        dictDetailDTO.setLabel( dictDetail.getLabel() );
        dictDetailDTO.setValue( dictDetail.getValue() );
        dictDetailDTO.setDictSort( dictDetail.getDictSort() );

        return dictDetailDTO;
    }

    protected List<DictDetailDTO> dictDetailListToDictDetailDTOList(List<DictDetail> list) {
        if ( list == null ) {
            return null;
        }

        List<DictDetailDTO> list1 = new ArrayList<DictDetailDTO>( list.size() );
        for ( DictDetail dictDetail : list ) {
            list1.add( dictDetailToDictDetailDTO( dictDetail ) );
        }

        return list1;
    }
}
