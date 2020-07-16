package com.dxj.module.system.domain.mapstruct;

import com.dxj.module.system.domain.dto.MenuDTO;
import com.dxj.module.system.domain.entity.Menu;
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
public class MenuMapperImpl implements MenuMapper {

    @Override
    public Menu toEntity(MenuDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Menu menu = new Menu();

        menu.setCreateBy( dto.getCreateBy() );
        menu.setUpdatedBy( dto.getUpdatedBy() );
        menu.setCreateTime( dto.getCreateTime() );
        menu.setUpdateTime( dto.getUpdateTime() );
        menu.setId( dto.getId() );
        menu.setTitle( dto.getTitle() );
        menu.setComponentName( dto.getComponentName() );
        menu.setMenuSort( dto.getMenuSort() );
        menu.setComponent( dto.getComponent() );
        menu.setPath( dto.getPath() );
        menu.setType( dto.getType() );
        menu.setPermission( dto.getPermission() );
        menu.setIcon( dto.getIcon() );
        menu.setCache( dto.getCache() );
        menu.setHidden( dto.getHidden() );
        menu.setPid( dto.getPid() );
        menu.setSubCount( dto.getSubCount() );
        menu.setIFrame( dto.getIFrame() );

        return menu;
    }

    @Override
    public MenuDTO toDto(Menu entity) {
        if ( entity == null ) {
            return null;
        }

        MenuDTO menuDTO = new MenuDTO();

        menuDTO.setCreateBy( entity.getCreateBy() );
        menuDTO.setUpdatedBy( entity.getUpdatedBy() );
        menuDTO.setCreateTime( entity.getCreateTime() );
        menuDTO.setUpdateTime( entity.getUpdateTime() );
        menuDTO.setId( entity.getId() );
        menuDTO.setType( entity.getType() );
        menuDTO.setPermission( entity.getPermission() );
        menuDTO.setTitle( entity.getTitle() );
        menuDTO.setMenuSort( entity.getMenuSort() );
        menuDTO.setPath( entity.getPath() );
        menuDTO.setComponent( entity.getComponent() );
        menuDTO.setPid( entity.getPid() );
        menuDTO.setSubCount( entity.getSubCount() );
        menuDTO.setIFrame( entity.getIFrame() );
        menuDTO.setCache( entity.getCache() );
        menuDTO.setHidden( entity.getHidden() );
        menuDTO.setComponentName( entity.getComponentName() );
        menuDTO.setIcon( entity.getIcon() );

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
