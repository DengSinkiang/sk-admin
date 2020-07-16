package com.dxj.module.system.domain.mapstruct;

import com.dxj.module.system.domain.dto.DictSmallDTO;
import com.dxj.module.system.domain.entity.Dict;
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
public class DictSmallMapperImpl implements DictSmallMapper {

    @Override
    public Dict toEntity(DictSmallDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Dict dict = new Dict();

        dict.setId( dto.getId() );

        return dict;
    }

    @Override
    public DictSmallDTO toDto(Dict entity) {
        if ( entity == null ) {
            return null;
        }

        DictSmallDTO dictSmallDTO = new DictSmallDTO();

        dictSmallDTO.setId( entity.getId() );

        return dictSmallDTO;
    }

    @Override
    public List<Dict> toEntity(List<DictSmallDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Dict> list = new ArrayList<Dict>( dtoList.size() );
        for ( DictSmallDTO dictSmallDTO : dtoList ) {
            list.add( toEntity( dictSmallDTO ) );
        }

        return list;
    }

    @Override
    public List<DictSmallDTO> toDto(List<Dict> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DictSmallDTO> list = new ArrayList<DictSmallDTO>( entityList.size() );
        for ( Dict dict : entityList ) {
            list.add( toDto( dict ) );
        }

        return list;
    }
}
