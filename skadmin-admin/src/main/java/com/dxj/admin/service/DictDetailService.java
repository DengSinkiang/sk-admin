package com.dxj.admin.service;

import com.dxj.admin.domain.DictDetail;
import com.dxj.admin.service.spec.DictDetailSpec;
import com.dxj.common.util.PageUtil;
import com.dxj.common.util.ValidationUtil;
import com.dxj.admin.repository.DictDetailRepository;
import com.dxj.admin.dto.DictDetailDTO;
import com.dxj.admin.mapper.DictDetailMapper;
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
@CacheConfig(cacheNames = "dictDetail")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictDetailService {

    private final DictDetailRepository dictDetailRepository;

    private final DictDetailMapper dictDetailMapper;

    @Autowired
    public DictDetailService(DictDetailRepository dictDetailRepository, DictDetailMapper dictDetailMapper) {
        this.dictDetailRepository = dictDetailRepository;
        this.dictDetailMapper = dictDetailMapper;
    }

    @Cacheable(key = "#p0")
    public DictDetailDTO findById(Long id) {
        Optional<DictDetail> dictDetail = dictDetailRepository.findById(id);
        ValidationUtil.isNull(dictDetail, "DictDetail", "id", id);
        return dictDetailMapper.toDto(dictDetail.orElse(null));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public DictDetailDTO create(DictDetail resources) {
        return dictDetailMapper.toDto(dictDetailRepository.save(resources));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(DictDetail resources) {
        Optional<DictDetail> optionalDictDetail = dictDetailRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalDictDetail, "DictDetail", "id", resources.getId());

        DictDetail dictDetail = optionalDictDetail.orElse(null);
        // 此处需自己修改
        assert dictDetail != null;
        resources.setId(dictDetail.getId());
        dictDetailRepository.save(resources);
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        dictDetailRepository.deleteById(id);
    }

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Map<String, Object> queryAll(DictDetailDTO dictDetail, Pageable pageable) {
        Page<DictDetail> page = dictDetailRepository.findAll(DictDetailSpec.getSpec(dictDetail), pageable);
        return PageUtil.toPage(page.map(dictDetailMapper::toDto));
    }
}
