package com.dxj.module.system.domain.mapstruct;

import com.dxj.module.system.domain.dto.JobDTO;
import com.dxj.module.system.domain.entity.Job;
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
public class JobMapperImpl implements JobMapper {

    @Override
    public Job toEntity(JobDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Job job = new Job();

        job.setCreateBy( dto.getCreateBy() );
        job.setUpdatedBy( dto.getUpdatedBy() );
        job.setCreateTime( dto.getCreateTime() );
        job.setUpdateTime( dto.getUpdateTime() );
        job.setId( dto.getId() );
        job.setName( dto.getName() );
        if ( dto.getJobSort() != null ) {
            job.setJobSort( dto.getJobSort().longValue() );
        }
        job.setEnabled( dto.getEnabled() );

        return job;
    }

    @Override
    public JobDTO toDto(Job entity) {
        if ( entity == null ) {
            return null;
        }

        JobDTO jobDTO = new JobDTO();

        jobDTO.setCreateBy( entity.getCreateBy() );
        jobDTO.setUpdatedBy( entity.getUpdatedBy() );
        jobDTO.setCreateTime( entity.getCreateTime() );
        jobDTO.setUpdateTime( entity.getUpdateTime() );
        jobDTO.setId( entity.getId() );
        if ( entity.getJobSort() != null ) {
            jobDTO.setJobSort( entity.getJobSort().intValue() );
        }
        jobDTO.setName( entity.getName() );
        jobDTO.setEnabled( entity.getEnabled() );

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
