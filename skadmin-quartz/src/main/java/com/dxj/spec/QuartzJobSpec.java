package com.dxj.spec;

import com.dxj.domain.QuartzJob;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dxj
 * @Date: 2019-05-30 16:43
 */
public class QuartzJobSpec {
    public static Specification<QuartzJob> getSpec(QuartzJob quartzJob) {
        return (Specification<QuartzJob>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(quartzJob.getJobName())) {
                //模糊
                list.add(cb.like(root.get("jobName").as(String.class), "%" + quartzJob.getJobName() + "%"));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }
}
