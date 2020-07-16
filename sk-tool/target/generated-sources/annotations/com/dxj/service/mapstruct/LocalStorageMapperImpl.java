package com.dxj.service.mapstruct;

import com.dxj.domain.dto.LocalStorageDTO;
import com.dxj.domain.entity.LocalStorage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-16T19:44:19+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
@Component
public class LocalStorageMapperImpl implements LocalStorageMapper {

    @Override
    public LocalStorage toEntity(LocalStorageDTO dto) {
        if ( dto == null ) {
            return null;
        }

        LocalStorage localStorage = new LocalStorage();

        localStorage.setCreateBy( dto.getCreateBy() );
        localStorage.setUpdatedBy( dto.getUpdatedBy() );
        localStorage.setCreateTime( dto.getCreateTime() );
        localStorage.setUpdateTime( dto.getUpdateTime() );
        localStorage.setId( dto.getId() );
        localStorage.setRealName( dto.getRealName() );
        localStorage.setName( dto.getName() );
        localStorage.setSuffix( dto.getSuffix() );
        localStorage.setType( dto.getType() );
        localStorage.setSize( dto.getSize() );

        return localStorage;
    }

    @Override
    public LocalStorageDTO toDto(LocalStorage entity) {
        if ( entity == null ) {
            return null;
        }

        LocalStorageDTO localStorageDTO = new LocalStorageDTO();

        localStorageDTO.setCreateBy( entity.getCreateBy() );
        localStorageDTO.setUpdatedBy( entity.getUpdatedBy() );
        localStorageDTO.setCreateTime( entity.getCreateTime() );
        localStorageDTO.setUpdateTime( entity.getUpdateTime() );
        localStorageDTO.setId( entity.getId() );
        localStorageDTO.setRealName( entity.getRealName() );
        localStorageDTO.setName( entity.getName() );
        localStorageDTO.setSuffix( entity.getSuffix() );
        localStorageDTO.setType( entity.getType() );
        localStorageDTO.setSize( entity.getSize() );

        return localStorageDTO;
    }

    @Override
    public List<LocalStorage> toEntity(List<LocalStorageDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<LocalStorage> list = new ArrayList<LocalStorage>( dtoList.size() );
        for ( LocalStorageDTO localStorageDTO : dtoList ) {
            list.add( toEntity( localStorageDTO ) );
        }

        return list;
    }

    @Override
    public List<LocalStorageDTO> toDto(List<LocalStorage> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<LocalStorageDTO> list = new ArrayList<LocalStorageDTO>( entityList.size() );
        for ( LocalStorage localStorage : entityList ) {
            list.add( toDto( localStorage ) );
        }

        return list;
    }
}
