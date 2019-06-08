package com.dxj.admin.service.spec;

import com.dxj.admin.domain.Menu;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dxj
 * @Date: 2019-05-29 09:26
 */
public class MenuSpec {
    public static Specification<Menu> getSpec(String name) {
        return (Specification<Menu>) (root, query, cb) -> {
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
