package com.dxj.admin.service;

import com.dxj.admin.domain.Job;
import com.dxj.admin.dto.JobDTO;
import com.dxj.admin.mapper.JobMapper;
import com.dxj.admin.repository.JobRepository;
import com.dxj.admin.service.spec.JobSpec;
import com.dxj.common.util.PageUtils;
import com.dxj.common.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
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
        ValidationUtils.isNull(job, "Job", "id", id);
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
        ValidationUtils.isNull(optionalJob, "Job", "id", resources.getId());

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
    public Map<String, Object> queryAll(String name, Boolean enabled, Set<Long> deptIds, Long deptId, Pageable pageable) {
        Page<Job> page = jobRepository.findAll(JobSpec.getSpec(new JobDTO(name, enabled), deptIds, deptId), pageable);
        return PageUtils.toPage(page.map(jobMapper::toDto));
    }
}
