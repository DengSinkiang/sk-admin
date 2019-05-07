package com.dxj.modules.system.query;

import com.dxj.modules.system.domain.Dept;
import com.dxj.utils.PageUtil;
import com.dxj.modules.system.domain.Job;
import com.dxj.modules.system.dto.JobDTO;
import com.dxj.modules.system.repository.JobRepository;
import com.dxj.modules.system.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author dxj
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "job")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobQueryService {

    private final JobRepository jobRepository;

    private final JobMapper jobMapper;

    @Autowired
    public JobQueryService(JobRepository jobRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(String name, Boolean enabled, Set<Long> deptIds, Long deptId, Pageable pageable) {
        Page<Job> page = jobRepository.findAll(new Spec(new JobDTO(name, enabled), deptIds, deptId), pageable);
        return PageUtil.toPage(page.map(jobMapper::toDto));
    }

    class Spec implements Specification<Job> {

        private JobDTO job;
        private Long deptId;
        private Set<Long> deptIds;

        Spec(JobDTO job, Set<Long> deptIds, Long deptId) {
            this.job = job;
            this.deptId = deptId;
            this.deptIds = deptIds;
        }

        @Override
        public Predicate toPredicate(Root<Job> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<>();

            // 数据权限
            Join<Dept, Job> join = root.join("dept", JoinType.LEFT);
            if (!CollectionUtils.isEmpty(deptIds)) {
                list.add(join.get("id").in(deptIds));
            }

            if (!ObjectUtils.isEmpty(job.getEnabled())) {
                //精确
                list.add(cb.equal(root.get("enabled").as(Boolean.class), job.getEnabled()));
            }

            if (!ObjectUtils.isEmpty(job.getName())) {
                //模糊
                list.add(cb.like(root.get("name").as(String.class), "%" + job.getName() + "%"));
            }

            if (deptId != null) {
                //精确
                list.add(cb.equal(join.get("id").as(Long.class), deptId));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
