package com.dxj.system.service.spec;

import com.dxj.system.domain.Dict;
import com.dxj.system.dto.DictDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dxj
 * @Date: 2019-05-29 08:55
 */
public class DictSpec {
    public static Specification<Dict> getSpec(DictDTO dictDTO) {
        return (Specification<Dict>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(dictDTO.getName())) {
                //模糊
                list.add(cb.like(root.get("name").as(String.class), "%" + dictDTO.getName() + "%"));
            }
            if (!ObjectUtils.isEmpty(dictDTO.getRemark())) {
                //模糊
                list.add(cb.like(root.get("remark").as(String.class), "%" + dictDTO.getRemark() + "%"));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }
}
