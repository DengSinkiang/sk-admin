package com.dxj.admin.service.spec;

import com.dxj.admin.domain.Dept;
import com.dxj.admin.dto.DeptDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeptSpec {
    public static Specification<Dept> getSpec(DeptDTO dept, Set<Long> deptIds) {
        return (Specification<Dept>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(dept.getEnabled())) {
                //相等
                list.add(cb.equal(root.get("enabled").as(Boolean.class), dept.getEnabled()));
            }

            if (!ObjectUtils.isEmpty(dept.getPid())) {
                //相等
                list.add(cb.equal(root.get("pid").as(Boolean.class), dept.getPid()));
            }

            if (!CollectionUtils.isEmpty(deptIds)) {
                list.add(root.get("id").in(deptIds));
            }

            if (!ObjectUtils.isEmpty(dept.getName())) {
                //模糊
                list.add(cb.like(root.get("name").as(String.class), "%" + dept.getName() + "%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }
}
