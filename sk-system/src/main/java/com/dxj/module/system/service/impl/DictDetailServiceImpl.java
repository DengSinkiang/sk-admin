package com.dxj.module.system.service.impl;

import com.dxj.module.system.dao.DictDao;
import com.dxj.module.system.dao.DictDetailDao;
import com.dxj.module.system.domain.entity.Dict;
import com.dxj.module.system.domain.entity.DictDetail;
import com.dxj.module.system.service.DictDetailService;
import com.dxj.module.system.domain.dto.DictDetailDTO;
import com.dxj.module.system.domain.query.DictDetailQuery;
import com.dxj.module.system.domain.mapstruct.DictDetailMapper;
import com.dxj.util.PageUtil;
import com.dxj.util.QueryHelp;
import com.dxj.util.RedisUtils;
import com.dxj.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Sinkiang
 * @date 2019-04-10
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dict")
public class DictDetailServiceImpl implements DictDetailService {

    private final DictDao dictRepository;
    private final DictDetailDao dictDetailRepository;
    private final DictDetailMapper dictDetailMapper;
    private final RedisUtils redisUtils;

    @Override
    public Map<String, Object> queryAll(DictDetailQuery criteria, Pageable pageable) {
        Page<DictDetail> page = dictDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(dictDetailMapper::toDto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(DictDetail resources) {
        dictDetailRepository.save(resources);
        // 清理缓存
        delCaches(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DictDetail resources) {
        DictDetail dictDetail = dictDetailRepository.findById(resources.getId()).orElseGet(DictDetail::new);
        ValidationUtil.isNull(dictDetail.getId(), "DictDetail", "id", resources.getId());
        resources.setId(dictDetail.getId());
        dictDetailRepository.save(resources);
        // 清理缓存
        delCaches(resources);
    }

    @Override
    @Cacheable(key = "'name:' + #p0")
    public List<DictDetailDTO> getDictByName(String name) {
        return dictDetailMapper.toDto(dictDetailRepository.findByDictName(name));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        DictDetail dictDetail = dictDetailRepository.findById(id).orElseGet(DictDetail::new);
        // 清理缓存
        delCaches(dictDetail);
        dictDetailRepository.deleteById(id);
    }

    private void delCaches(DictDetail dictDetail) {
        Dict dict = dictRepository.findById(dictDetail.getDict().getId()).orElseGet(Dict::new);
        redisUtils.del("dept::name:" + dict.getName());
    }
}
