package com.dxj.module.system.domain.mapstruct;

import com.dxj.module.system.domain.dto.DeptDTO;
import com.dxj.module.system.domain.entity.Dept;
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
public class DeptMapperImpl implements DeptMapper {

    @Override
    public Dept toEntity(DeptDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Dept dept = new Dept();

        dept.setCreateBy( dto.getCreateBy() );
        dept.setUpdatedBy( dto.getUpdatedBy() );
        dept.setCreateTime( dto.getCreateTime() );
        dept.setUpdateTime( dto.getUpdateTime() );
        dept.setId( dto.getId() );
        dept.setDeptSort( dto.getDeptSort() );
        dept.setName( dto.getName() );
        dept.setEnabled( dto.getEnabled() );
        dept.setPid( dto.getPid() );
        dept.setSubCount( dto.getSubCount() );

        return dept;
    }

    @Override
    public DeptDTO toDto(Dept entity) {
        if ( entity == null ) {
            return null;
        }

        DeptDTO deptDTO = new DeptDTO();

        deptDTO.setCreateBy( entity.getCreateBy() );
        deptDTO.setUpdatedBy( entity.getUpdatedBy() );
        deptDTO.setCreateTime( entity.getCreateTime() );
        deptDTO.setUpdateTime( entity.getUpdateTime() );
        deptDTO.setId( entity.getId() );
        deptDTO.setName( entity.getName() );
        deptDTO.setEnabled( entity.getEnabled() );
        deptDTO.setDeptSort( entity.getDeptSort() );
        deptDTO.setPid( entity.getPid() );
        deptDTO.setSubCount( entity.getSubCount() );

        return deptDTO;
    }

    @Override
    public List<Dept> toEntity(List<DeptDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Dept> list = new ArrayList<Dept>( dtoList.size() );
        for ( DeptDTO deptDTO : dtoList ) {
            list.add( toEntity( deptDTO ) );
        }

        return list;
    }

    @Override
    public List<DeptDTO> toDto(List<Dept> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DeptDTO> list = new ArrayList<DeptDTO>( entityList.size() );
        for ( Dept dept : entityList ) {
            list.add( toDto( dept ) );
        }

        return list;
    }
}
