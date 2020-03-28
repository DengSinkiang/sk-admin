package com.dxj.module.system.service.impl;

import com.dxj.module.system.dao.DeptDao;
import com.dxj.module.system.dao.JobDao;
import com.dxj.module.system.domain.entity.Job;
import com.dxj.module.system.service.JobService;
import com.dxj.module.system.domain.dto.JobDTO;
import com.dxj.module.system.domain.query.JobQuery;
import com.dxj.module.system.domain.mapper.JobMapper;
import com.dxj.util.FileUtils;
import com.dxj.util.PageUtil;
import com.dxj.util.QueryHelp;
import com.dxj.util.ValidationUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
* @author Sinkiang
* @date 2019-03-29
*/
@Service
@CacheConfig(cacheNames = "job")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl implements JobService {

    private final JobDao jobRepository;

    private final JobMapper jobMapper;

    private final DeptDao deptRepository;

    public JobServiceImpl(JobDao jobRepository, JobMapper jobMapper, DeptDao deptRepository) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.deptRepository = deptRepository;
    }

    @Override
    @Cacheable
    public Map<String,Object> queryAll(JobQuery criteria, Pageable pageable) {
        Page<Job> page = jobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<JobDTO> jobs = new ArrayList<>();
        for (Job job : page.getContent()) {
            jobs.add(jobMapper.toDto(job,deptRepository.findNameById(job.getDept().getPid())));
        }
        return PageUtil.toPage(jobs,page.getTotalElements());
    }

    @Override
    @Cacheable
    public List<JobDTO> queryAll(JobQuery criteria) {
        List<Job> list = jobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
        return jobMapper.toDto(list);
    }

    @Override
    @Cacheable(key = "#p0")
    public JobDTO findById(Long id) {
        Job job = jobRepository.findById(id).orElseGet(Job::new);
        ValidationUtil.isNull(job.getId(),"Job","id",id);
        return jobMapper.toDto(job);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public JobDTO create(Job resources) {
        return jobMapper.toDto(jobRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Job resources) {
        Job job = jobRepository.findById(resources.getId()).orElseGet(Job::new);
        ValidationUtil.isNull( job.getId(),"Job","id",resources.getId());
        resources.setId(job.getId());
        jobRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            jobRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<JobDTO> jobDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (JobDTO jobDTO : jobDtos) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("岗位名称", jobDTO.getName());
            map.put("所属部门", jobDTO.getDept().getName());
            map.put("岗位状态", jobDTO.getEnabled() ? "启用" : "停用");
            map.put("创建日期", jobDTO.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }
}
