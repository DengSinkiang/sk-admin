package com.dxj.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dxj.module.system.dao.DictDao;
import com.dxj.module.system.domain.entity.Dict;
import com.dxj.module.system.service.DictService;
import com.dxj.module.system.domain.dto.DictDetailDTO;
import com.dxj.module.system.domain.dto.DictDTO;
import com.dxj.module.system.domain.query.DictQuery;
import com.dxj.module.system.domain.mapper.DictMapper;
import com.dxj.util.FileUtils;
import com.dxj.util.PageUtil;
import com.dxj.util.QueryHelp;
import com.dxj.util.ValidationUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @author Sinkiang
* @date 2019-04-10
*/
@Service
@CacheConfig(cacheNames = "dict")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictServiceImpl implements DictService {

    private final DictDao dictRepository;

    private final DictMapper dictMapper;

    public DictServiceImpl(DictDao dictRepository, DictMapper dictMapper) {
        this.dictRepository = dictRepository;
        this.dictMapper = dictMapper;
    }

    @Override
    @Cacheable
    public Map<String, Object> queryAll(DictQuery dict, Pageable pageable){
        Page<Dict> page = dictRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, dict, cb), pageable);
        return PageUtil.toPage(page.map(dictMapper::toDto));
    }

    @Override
    public List<DictDTO> queryAll(DictQuery dict) {
        List<Dict> list = dictRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, dict, cb));
        return dictMapper.toDto(list);
    }

    @Override
    @Cacheable(key = "#p0")
    public DictDTO findById(Long id) {
        Dict dict = dictRepository.findById(id).orElseGet(Dict::new);
        ValidationUtil.isNull(dict.getId(),"Dict","id",id);
        return dictMapper.toDto(dict);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public DictDTO create(Dict resources) {
        return dictMapper.toDto(dictRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Dict resources) {
        Dict dict = dictRepository.findById(resources.getId()).orElseGet(Dict::new);
        ValidationUtil.isNull( dict.getId(),"Dict","id",resources.getId());
        resources.setId(dict.getId());
        dictRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        dictRepository.deleteById(id);
    }

    @Override
    public void download(List<DictDTO> dictDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DictDTO dictDTO : dictDtos) {
            if(CollectionUtil.isNotEmpty(dictDTO.getDictDetails())){
                for (DictDetailDTO dictDetail : dictDTO.getDictDetails()) {
                    Map<String,Object> map = new LinkedHashMap<>();
                    map.put("字典名称", dictDTO.getName());
                    map.put("字典描述", dictDTO.getRemark());
                    map.put("字典标签", dictDetail.getLabel());
                    map.put("字典值", dictDetail.getValue());
                    map.put("创建日期", dictDetail.getCreateTime());
                    list.add(map);
                }
            } else {
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("字典名称", dictDTO.getName());
                map.put("字典描述", dictDTO.getRemark());
                map.put("字典标签", null);
                map.put("字典值", null);
                map.put("创建日期", dictDTO.getCreateTime());
                list.add(map);
            }
        }
        FileUtils.downloadExcel(list, response);
    }
}
