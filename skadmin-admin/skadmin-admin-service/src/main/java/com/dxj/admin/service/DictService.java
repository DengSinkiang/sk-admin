package com.dxj.admin.service;

import com.dxj.admin.domain.entity.Dict;
import com.dxj.admin.domain.dto.DictDTO;
import com.dxj.admin.mapper.DictMapper;
import com.dxj.admin.domain.query.DictQuery;
import com.dxj.admin.repository.DictRepository;
import com.dxj.common.util.BaseQuery;
import com.dxj.common.util.PageUtil;
import com.dxj.common.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

/**
 * @author dxj
 * @date 2019-04-10
 */
@Service
@CacheConfig(cacheNames = "dict")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictService {

    private final DictRepository dictRepository;

    private final DictMapper dictMapper;

    @Autowired
    public DictService(DictRepository dictRepository, DictMapper dictMapper) {
        this.dictRepository = dictRepository;
        this.dictMapper = dictMapper;
    }

    @Cacheable(key = "#p0")
    public DictDTO findById(Long id) {
        Optional<Dict> dict = dictRepository.findById(id);
        ValidationUtil.isNull(dict, "Dict", "id", id);
        return dictMapper.toDto(dict.orElse(null));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public DictDTO create(Dict resources) {
        return dictMapper.toDto(dictRepository.save(resources));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Dict resources) {
        Optional<Dict> optionalDict = dictRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalDict, "Dict", "id", resources.getId());

        Dict dict = optionalDict.orElse(null);
        // 此处需自己修改
        assert dict != null;
        resources.setId(dict.getId());
        dictRepository.save(resources);
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        dictRepository.deleteById(id);
    }

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Map<String, Object> queryAll(DictQuery query, Pageable pageable) {
        Page<Dict> page = dictRepository.findAll((root, criteriaQuery, criteriaBuilder) -> BaseQuery.getPredicate(root, query, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(dictMapper::toDto));
    }


}
