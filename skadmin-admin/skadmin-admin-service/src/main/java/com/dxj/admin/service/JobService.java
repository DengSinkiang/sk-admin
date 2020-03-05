package com.dxj.admin.service;

import com.dxj.admin.domain.entity.Job;
import com.dxj.admin.domain.dto.JobDTO;
import com.dxj.admin.mapper.JobMapper;
import com.dxj.admin.domain.query.JobQuery;
import com.dxj.admin.repository.DeptRepository;
import com.dxj.admin.repository.JobRepository;
import com.dxj.common.util.BaseQuery;
import com.dxj.common.util.PageUtil;
import com.dxj.common.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author dxj
 * @date 2019-03-29
 */
@Service
@CacheConfig(cacheNames = "job")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobService {

    private final JobRepository jobRepository;

    private final DeptRepository deptRepository;

    private final JobMapper jobMapper;

    @Autowired
    public JobService(JobRepository jobRepository, JobMapper jobMapper, DeptRepository deptRepository) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.deptRepository = deptRepository;
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
    public Map<String, Object> queryAll(JobQuery query, Pageable pageable) {
        Page<Job> page = jobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> BaseQuery.getPredicate(root, query, criteriaBuilder), pageable);
        List<JobDTO> jobs = new ArrayList<>();
        for (Job job : page.getContent()) {
            jobs.add(jobMapper.toDto(job,deptRepository.findNameById(job.getDept().getPid())));
        }
        return PageUtil.toPage(jobs,page.getTotalElements());
    }
}
