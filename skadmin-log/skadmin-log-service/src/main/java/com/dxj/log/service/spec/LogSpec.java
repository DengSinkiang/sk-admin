package com.dxj.log.service.spec;

import com.dxj.log.domain.Log;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dxj
 * @date 2019-04-24
 */
public class LogSpec {
    public static Specification<Log> getSpec(Log log) {
        return (Specification<Log>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if(!ObjectUtils.isEmpty(log.getUsername())){
                list.add(cb.like(root.get("username").as(String.class),"%"+log.getUsername()+"%"));
            }

            if (!ObjectUtils.isEmpty(log.getLogType())) {
                list.add(cb.equal(root.get("logType").as(String.class), log.getLogType()));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }

}
