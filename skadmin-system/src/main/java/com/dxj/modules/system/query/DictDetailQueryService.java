package com.dxj.modules.system.query;

import com.dxj.modules.system.domain.Dict;
import com.dxj.utils.PageUtil;
import com.dxj.modules.system.domain.DictDetail;
import com.dxj.modules.system.dto.DictDetailDTO;
import com.dxj.modules.system.repository.DictDetailRepository;
import com.dxj.modules.system.mapper.DictDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dxj
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "dictDetail")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictDetailQueryService {

    private final DictDetailRepository dictDetailRepository;

    private final DictDetailMapper dictDetailMapper;

    @Autowired
    public DictDetailQueryService(DictDetailRepository dictDetailRepository, DictDetailMapper dictDetailMapper) {
        this.dictDetailRepository = dictDetailRepository;
        this.dictDetailMapper = dictDetailMapper;
    }

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(DictDetailDTO dictDetail, Pageable pageable){
        Page<DictDetail> page = dictDetailRepository.findAll(new Spec(dictDetail),pageable);
        return PageUtil.toPage(page.map(dictDetailMapper::toDto));
    }

    class Spec implements Specification<DictDetail> {

        private DictDetailDTO dictDetail;

        Spec(DictDetailDTO dictDetail){
            this.dictDetail = dictDetail;
        }

        @Override
        public Predicate toPredicate(Root<DictDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<>();

            Join<Dict,DictDetail> join = root.join("dict", JoinType.LEFT);

            if(!ObjectUtils.isEmpty(dictDetail.getLabel())){
                //模糊
                list.add(cb.like(root.get("label").as(String.class), "%" + dictDetail.getLabel() + "%"));
            }

            if(!ObjectUtils.isEmpty(dictDetail.getDictName())){
                list.add(cb.equal(join.get("name").as(String.class), dictDetail.getDictName()));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
