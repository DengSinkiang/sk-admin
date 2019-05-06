package com.dxj.modules.system.mapper;

import com.dxj.modules.system.domain.Permission;
import com.dxj.modules.system.dto.PermissionDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-05-06T10:41:51+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_192 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permission toEntity(PermissionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Permission permission = new Permission();

        permission.setId( dto.getId() );
        permission.setName( dto.getName() );
        permission.setPid( dto.getPid() );
        permission.setAlias( dto.getAlias() );
        permission.setCreateTime( dto.getCreateTime() );

        return permission;
    }

    @Override
    public PermissionDTO toDto(Permission entity) {
        if ( entity == null ) {
            return null;
        }

        PermissionDTO permissionDTO = new PermissionDTO();

        permissionDTO.setId( entity.getId() );
        permissionDTO.setName( entity.getName() );
        permissionDTO.setPid( entity.getPid() );
        permissionDTO.setAlias( entity.getAlias() );
        permissionDTO.setCreateTime( entity.getCreateTime() );

        return permissionDTO;
    }

    @Override
    public List<Permission> toEntity(List<PermissionDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Permission> list = new ArrayList<Permission>( dtoList.size() );
        for ( PermissionDTO permissionDTO : dtoList ) {
            list.add( toEntity( permissionDTO ) );
        }

        return list;
    }

    @Override
    public List<PermissionDTO> toDto(List<Permission> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PermissionDTO> list = new ArrayList<PermissionDTO>( entityList.size() );
        for ( Permission permission : entityList ) {
            list.add( toDto( permission ) );
        }

        return list;
    }
}
