package com.dxj.system.service.spec;

import com.dxj.system.domain.Role;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dxj
 * @Date: 2019-05-29 09:41
 */
public class RoleSpec {
    public static Specification<Role> getSpec(String name) {
        return (Specification<Role>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(name)) {
                //模糊
                list.add(cb.like(root.get("name").as(String.class), "%" + name + "%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }
}
