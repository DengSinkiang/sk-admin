package com.dxj.modules.system.query;

import com.dxj.modules.system.domain.Dept;
import com.dxj.modules.system.domain.User;
import com.dxj.modules.system.repository.UserRepository;
import com.dxj.modules.system.dto.UserDTO;
import com.dxj.modules.system.mapper.UserMapper;
import com.dxj.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author dxj
 * @date 2018-11-22
 */
@Service
@CacheConfig(cacheNames = "user")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserQueryService {

    private final UserRepository userRepo;

    private final UserMapper userMapper;

    @Autowired
    public UserQueryService(UserRepository userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }


    class Spec implements Specification<User> {

        private UserDTO user;

        private Set<Long> deptIds;

        Spec(UserDTO user, Set<Long> deptIds){
            this.deptIds = deptIds;
            this.user = user;
        }

        @Override
        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<>();

            // 数据权限， 关联查询
            Join<Dept,User> join = root.join("dept", JoinType.LEFT);
            if (!CollectionUtils.isEmpty(deptIds)) {
                list.add(join.get("id").in(deptIds));
            }

            if (!ObjectUtils.isEmpty(user.getId())) {
                //相等
                list.add(cb.equal(root.get("id").as(Long.class),user.getId()));
            }

            if (!ObjectUtils.isEmpty(user.getEnabled())) {
                //相等
                list.add(cb.equal(root.get("enabled").as(Boolean.class),user.getEnabled()));
            }


            if (!ObjectUtils.isEmpty(user.getUsername())) {
                //模糊
                list.add(cb.like(root.get("username").as(String.class),"%"+user.getUsername()+"%"));
            }

            if (!ObjectUtils.isEmpty(user.getEmail())) {
                //模糊
                list.add(cb.like(root.get("email").as(String.class),"%" + user.getEmail() + "%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Map<String, Object> queryAll(UserDTO user, Set<Long> deptIds, Pageable pageable){
        Page<User> page = userRepo.findAll(new Spec(user, deptIds), pageable);
        return PageUtil.toPage(page.map(userMapper::toDto));
    }
}
