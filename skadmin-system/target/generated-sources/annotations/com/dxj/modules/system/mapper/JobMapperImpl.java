package com.dxj.modules.system.mapper;

import com.dxj.modules.system.domain.Job;
import com.dxj.modules.system.dto.JobDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-05-21T08:42:53+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_192 (Oracle Corporation)"
)
@Component
public class JobMapperImpl implements JobMapper {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public Job toEntity(JobDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Job job = new Job();

        job.setId( dto.getId() );
        job.setName( dto.getName() );
        job.setSort( dto.getSort() );
        job.setEnabled( dto.getEnabled() );
        job.setDept( deptMapper.toEntity( dto.getDept() ) );
        job.setCreateTime( dto.getCreateTime() );

        return job;
    }

    @Override
    public JobDTO toDto(Job entity) {
        if ( entity == null ) {
            return null;
        }

        JobDTO jobDTO = new JobDTO();

        jobDTO.setId( entity.getId() );
        jobDTO.setSort( entity.getSort() );
        jobDTO.setName( entity.getName() );
        jobDTO.setEnabled( entity.getEnabled() );
        jobDTO.setDept( deptMapper.toDto( entity.getDept() ) );
        jobDTO.setCreateTime( entity.getCreateTime() );

        return jobDTO;
    }

    @Override
    public List<Job> toEntity(List<JobDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Job> list = new ArrayList<Job>( dtoList.size() );
        for ( JobDTO jobDTO : dtoList ) {
            list.add( toEntity( jobDTO ) );
        }

        return list;
    }

    @Override
    public List<JobDTO> toDto(List<Job> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<JobDTO> list = new ArrayList<JobDTO>( entityList.size() );
        for ( Job job : entityList ) {
            list.add( toDto( job ) );
        }

        return list;
    }
}
