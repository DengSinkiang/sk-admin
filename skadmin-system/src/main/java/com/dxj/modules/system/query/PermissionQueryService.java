package com.dxj.modules.system.query;

import com.dxj.modules.system.domain.Permission;
import com.dxj.modules.system.repository.PermissionRepository;
import com.dxj.modules.system.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dxj
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "permission")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PermissionQueryService {

    private final PermissionRepository permissionRepository;

    private final PermissionMapper permissionMapper;

    @Autowired
    public PermissionQueryService(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    /**
     * 不分页
     */
    @Cacheable(key = "'queryAll:'+#p0")
    public List queryAll(String name) {
        return permissionMapper.toDto(permissionRepository.findAll(new Spec(name)));
    }

    class Spec implements Specification<Permission> {

        private String name;

        Spec(String name) {
            this.name = name;
        }

        @Override
        public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(name)) {
                //模糊
                list.add(cb.like(root.get("name").as(String.class), "%" + name + "%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
