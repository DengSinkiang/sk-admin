package com.dxj.admin.mapper;

import com.dxj.admin.domain.Dept;
import com.dxj.admin.dto.DeptSmallDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-15T13:05:22+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
@Component
public class DeptSmallMapperImpl implements DeptSmallMapper {

    @Override
    public Dept toEntity(DeptSmallDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Dept dept = new Dept();

        dept.setId( dto.getId() );
        dept.setName( dto.getName() );

        return dept;
    }

    @Override
    public DeptSmallDTO toDto(Dept entity) {
        if ( entity == null ) {
            return null;
        }

        DeptSmallDTO deptSmallDTO = new DeptSmallDTO();

        deptSmallDTO.setId( entity.getId() );
        deptSmallDTO.setName( entity.getName() );

        return deptSmallDTO;
    }

    @Override
    public List<Dept> toEntity(List<DeptSmallDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Dept> list = new ArrayList<Dept>( dtoList.size() );
        for ( DeptSmallDTO deptSmallDTO : dtoList ) {
            list.add( toEntity( deptSmallDTO ) );
        }

        return list;
    }

    @Override
    public List<DeptSmallDTO> toDto(List<Dept> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DeptSmallDTO> list = new ArrayList<DeptSmallDTO>( entityList.size() );
        for ( Dept dept : entityList ) {
            list.add( toDto( dept ) );
        }

        return list;
    }
}
