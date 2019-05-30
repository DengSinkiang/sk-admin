package com.dxj.system.spec;

import com.dxj.system.domain.Dept;
import com.dxj.system.domain.User;
import com.dxj.system.dto.UserDTO;
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
 * @Date: 2019-05-29 09:46
 */
public class UserSpec {
    public static Specification<User> getSpec(UserDTO user, Set<Long> deptIds) {
        return (Specification<User>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            // 数据权限， 关联查询
            Join<Dept, User> join = root.join("dept", JoinType.LEFT);
            if (!CollectionUtils.isEmpty(deptIds)) {
                list.add(join.get("id").in(deptIds));
            }

            if (!ObjectUtils.isEmpty(user.getId())) {
                //相等
                list.add(cb.equal(root.get("id").as(Long.class), user.getId()));
            }

            if (!ObjectUtils.isEmpty(user.getEnabled())) {
                //相等
                list.add(cb.equal(root.get("enabled").as(Boolean.class), user.getEnabled()));
            }


            if (!ObjectUtils.isEmpty(user.getUsername())) {
                //模糊
                list.add(cb.like(root.get("username").as(String.class), "%" + user.getUsername() + "%"));
            }

            if (!ObjectUtils.isEmpty(user.getEmail())) {
                //模糊
                list.add(cb.like(root.get("email").as(String.class), "%" + user.getEmail() + "%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }
}
