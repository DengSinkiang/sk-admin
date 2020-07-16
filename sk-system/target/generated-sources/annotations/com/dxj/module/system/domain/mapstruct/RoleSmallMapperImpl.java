package com.dxj.module.system.domain.mapstruct;

import com.dxj.module.system.domain.dto.RoleSmallDTO;
import com.dxj.module.system.domain.entity.Role;
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
public class RoleSmallMapperImpl implements RoleSmallMapper {

    @Override
    public Role toEntity(RoleSmallDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( dto.getId() );
        role.setName( dto.getName() );
        role.setDataScope( dto.getDataScope() );
        role.setLevel( dto.getLevel() );

        return role;
    }

    @Override
    public RoleSmallDTO toDto(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleSmallDTO roleSmallDTO = new RoleSmallDTO();

        roleSmallDTO.setId( entity.getId() );
        roleSmallDTO.setName( entity.getName() );
        roleSmallDTO.setLevel( entity.getLevel() );
        roleSmallDTO.setDataScope( entity.getDataScope() );

        return roleSmallDTO;
    }

    @Override
    public List<Role> toEntity(List<RoleSmallDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( dtoList.size() );
        for ( RoleSmallDTO roleSmallDTO : dtoList ) {
            list.add( toEntity( roleSmallDTO ) );
        }

        return list;
    }

    @Override
    public List<RoleSmallDTO> toDto(List<Role> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RoleSmallDTO> list = new ArrayList<RoleSmallDTO>( entityList.size() );
        for ( Role role : entityList ) {
            list.add( toDto( role ) );
        }

        return list;
    }
}
