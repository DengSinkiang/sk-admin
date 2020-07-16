package com.dxj.module.system.service.impl;

import com.dxj.exception.EntityExistException;
import com.dxj.exception.SkException;
import com.dxj.module.system.dao.DeptDao;
import com.dxj.module.system.dao.JobDao;
import com.dxj.module.system.dao.UserDao;
import com.dxj.module.system.domain.entity.Job;
import com.dxj.module.system.service.JobService;
import com.dxj.module.system.domain.dto.JobDTO;
import com.dxj.module.system.domain.query.JobQuery;
import com.dxj.module.system.domain.mapstruct.JobMapper;
import com.dxj.util.*;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@CacheConfig(cacheNames = "job")
public class JobServiceImpl implements JobService {

    private final JobDao jobRepository;
    private final JobMapper jobMapper;
    private final RedisUtils redisUtils;
    private final UserDao userRepository;

    @Override
    public Map<String, Object> queryAll(JobQuery criteria, Pageable pageable) {
        Page<Job> page = jobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(jobMapper::toDto).getContent(), page.getTotalElements());
    }

    @Override
    public List<JobDTO> queryAll(JobQuery criteria) {
        List<Job> list = jobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return jobMapper.toDto(list);
    }

    @Override
    @Cacheable(key = "'id:' + #p0")
    public JobDTO findById(Long id) {
        Job job = jobRepository.findById(id).orElseGet(Job::new);
        ValidationUtil.isNull(job.getId(), "Job", "id", id);
        return jobMapper.toDto(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Job resources) {
        Job job = jobRepository.findByName(resources.getName());
        if (job != null) {
            throw new EntityExistException(Job.class, "name", resources.getName());
        }
        jobRepository.save(resources);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void update(Job resources) {
        Job job = jobRepository.findById(resources.getId()).orElseGet(Job::new);
        Job old = jobRepository.findByName(resources.getName());
        if (old != null && !old.getId().equals(resources.getId())) {
            throw new EntityExistException(Job.class, "name", resources.getName());
        }
        ValidationUtil.isNull(job.getId(), "Job", "id", resources.getId());
        resources.setId(job.getId());
        jobRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        jobRepository.deleteAllByIdIn(ids);
        // 删除缓存
        redisUtils.delByKeys("job::id:", ids);
    }

    @Override
    public void download(List<JobDTO> jobDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (JobDTO jobDTO : jobDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("岗位名称", jobDTO.getName());
            map.put("岗位状态", jobDTO.getEnabled() ? "启用" : "停用");
            map.put("创建日期", jobDTO.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }

    @Override
    public void verification(Set<Long> ids) {
        if (userRepository.countByJobs(ids) > 0) {
            throw new SkException("所选的岗位中存在用户关联，请解除关联再试！");
        }
    }
}
