package com.dxj.modules.system.query;

import com.dxj.modules.system.domain.Dept;
import com.dxj.modules.system.dto.DeptDTO;
import com.dxj.modules.system.repository.DeptRepository;
import com.dxj.modules.system.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author dxj
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "dept")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptQueryService {

    private final DeptRepository deptRepository;

    private final DeptMapper deptMapper;

    @Autowired
    public DeptQueryService(DeptRepository deptRepository, DeptMapper deptMapper) {
        this.deptRepository = deptRepository;
        this.deptMapper = deptMapper;
    }

    /**
     * 不分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public List queryAll(DeptDTO dept, Set<Long> deptIds) {
        return deptMapper.toDto(deptRepository.findAll(new Spec(dept, deptIds)));
    }

    class Spec implements Specification<Dept> {

        private DeptDTO dept;

        private Set<Long> deptIds;


        Spec(DeptDTO dept, Set<Long> deptIds) {
            this.dept = dept;
            this.deptIds = deptIds;
        }

        @Override
        public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

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
        }
    }
}
