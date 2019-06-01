package com.dxj.system.service;

import com.dxj.system.domain.Job;
import com.dxj.system.service.spec.JobSpec;
import com.dxj.utils.PageUtil;
import com.dxj.utils.ValidationUtil;
import com.dxj.system.repository.JobRepository;
import com.dxj.system.dto.JobDTO;
import com.dxj.system.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * @author dxj
 * @date 2019-03-29
 */
@Service
@CacheConfig(cacheNames = "job")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobService {

    private final JobRepository jobRepository;

    private final JobMapper jobMapper;

    @Autowired
    public JobService(JobRepository jobRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    @Cacheable(key = "#p0")
    public JobDTO findById(Long id) {
        Optional<Job> job = jobRepository.findById(id);
        ValidationUtil.isNull(job, "Job", "id", id);
        return jobMapper.toDto(job.orElse(null));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public JobDTO create(Job resources) {
        return jobMapper.toDto(jobRepository.save(resources));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Job resources) {
        Optional<Job> optionalJob = jobRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalJob, "Job", "id", resources.getId());

        Job job = optionalJob.orElse(null);
        // 此处需自己修改
        assert job != null;
        resources.setId(job.getId());
        jobRepository.save(resources);
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        jobRepository.deleteById(id);
    }

    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(String name, Boolean enabled, Set<Long> deptIds, Long deptId, Pageable pageable) {
        Page<Job> page = jobRepository.findAll(JobSpec.getSpec(new JobDTO(name, enabled), deptIds, deptId), pageable);
        return PageUtil.toPage(page.map(jobMapper::toDto));
    }
}
