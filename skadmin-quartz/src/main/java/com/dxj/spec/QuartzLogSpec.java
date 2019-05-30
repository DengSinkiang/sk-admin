package com.dxj.spec;

import com.dxj.domain.QuartzLog;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dxj
 * @Date: 2019-05-30 16:43
 */
public class QuartzLogSpec {
    public static Specification<QuartzLog> getSpec(QuartzLog quartzLog) {
        return (Specification<QuartzLog>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(quartzLog.getJobName())) {

                //模糊
                list.add(cb.like(root.get("jobName").as(String.class), "%" + quartzLog.getJobName() + "%"));
            }

            if (!ObjectUtils.isEmpty(quartzLog.getIsSuccess())) {
                list.add(cb.equal(root.get("isSuccess").as(Boolean.class), quartzLog.getIsSuccess()));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }
}
