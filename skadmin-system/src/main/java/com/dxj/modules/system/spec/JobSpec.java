package com.dxj.modules.system.spec;

import com.dxj.modules.system.domain.Dept;
import com.dxj.modules.system.domain.Job;
import com.dxj.modules.system.dto.JobDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: dxj
 * @Date: 2019-05-29 09:08
 */
public class JobSpec {
    public static Specification<Job> getSpec(JobDTO jobDTO, Set<Long> deptIds, Long deptId) {
        return (Specification<Job>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            // 数据权限
            Join<Dept, Job> join = root.join("dept", JoinType.LEFT);
            if (!CollectionUtils.isEmpty(deptIds)) {
                list.add(join.get("id").in(deptIds));
            }

            if (!ObjectUtils.isEmpty(jobDTO.getEnabled())) {
                //精确
                list.add(cb.equal(root.get("enabled").as(Boolean.class), jobDTO.getEnabled()));
            }

            if (!ObjectUtils.isEmpty(jobDTO.getName())) {
                //模糊
                list.add(cb.like(root.get("name").as(String.class), "%" + jobDTO.getName() + "%"));
            }

            if (deptId != null) {
                //精确
                list.add(cb.equal(join.get("id").as(Long.class), deptId));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }
}
