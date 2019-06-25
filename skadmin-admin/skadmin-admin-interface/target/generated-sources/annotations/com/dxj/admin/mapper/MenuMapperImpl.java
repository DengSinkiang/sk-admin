package com.dxj.admin.mapper;

import com.dxj.admin.domain.Menu;
import com.dxj.admin.dto.MenuDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-24T08:37:23+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_192 (Oracle Corporation)"
)
@Component
public class MenuMapperImpl implements MenuMapper {

    @Override
    public Menu toEntity(MenuDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Menu menu = new Menu();

        menu.setId( dto.getId() );
        menu.setName( dto.getName() );
        menu.setSort( dto.getSort() );
        menu.setPath( dto.getPath() );
        menu.setComponent( dto.getComponent() );
        menu.setIcon( dto.getIcon() );
        menu.setPid( dto.getPid() );
        menu.setIFrame( dto.getIFrame() );
        menu.setCreateTime( dto.getCreateTime() );

        return menu;
    }

    @Override
    public MenuDTO toDto(Menu entity) {
        if ( entity == null ) {
            return null;
        }

        MenuDTO menuDTO = new MenuDTO();

        menuDTO.setId( entity.getId() );
        menuDTO.setName( entity.getName() );
        menuDTO.setSort( entity.getSort() );
        menuDTO.setPath( entity.getPath() );
        menuDTO.setComponent( entity.getComponent() );
        menuDTO.setPid( entity.getPid() );
        menuDTO.setIFrame( entity.getIFrame() );
        menuDTO.setIcon( entity.getIcon() );
        menuDTO.setCreateTime( entity.getCreateTime() );

        return menuDTO;
    }

    @Override
    public List<Menu> toEntity(List<MenuDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Menu> list = new ArrayList<Menu>( dtoList.size() );
        for ( MenuDTO menuDTO : dtoList ) {
            list.add( toEntity( menuDTO ) );
        }

        return list;
    }

    @Override
    public List<MenuDTO> toDto(List<Menu> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MenuDTO> list = new ArrayList<MenuDTO>( entityList.size() );
        for ( Menu menu : entityList ) {
            list.add( toDto( menu ) );
        }

        return list;
    }
}
