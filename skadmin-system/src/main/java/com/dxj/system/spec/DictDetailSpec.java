package com.dxj.system.spec;

import com.dxj.system.domain.Dict;
import com.dxj.system.domain.DictDetail;
import com.dxj.system.dto.DictDetailDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dxj
 * @Date: 2019-05-29 08:38
 */
public class DictDetailSpec {
    public static Specification<DictDetail> getSpec(DictDetailDTO dictDetail) {
        return (Specification<DictDetail>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            Join<Dict, DictDetail> join = root.join("dict", JoinType.LEFT);

            if (!ObjectUtils.isEmpty(dictDetail.getLabel())) {
                //模糊
                list.add(cb.like(root.get("label").as(String.class), "%" + dictDetail.getLabel() + "%"));
            }

            if (!ObjectUtils.isEmpty(dictDetail.getDictName())) {
                list.add(cb.equal(join.get("name").as(String.class), dictDetail.getDictName()));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }
}
