package com.dxj.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dxj.module.system.dao.DictDao;
import com.dxj.module.system.domain.entity.Dict;
import com.dxj.module.system.service.DictService;
import com.dxj.module.system.domain.dto.DictDetailDTO;
import com.dxj.module.system.domain.dto.DictDTO;
import com.dxj.module.system.domain.query.DictQuery;
import com.dxj.module.system.domain.mapstruct.DictMapper;
import com.dxj.util.*;
import lombok.RequiredArgsConstructor;
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
import java.util.*;

/**
* @author Sinkiang
* @date 2019-04-10
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dict")
public class DictServiceImpl implements DictService {

    private final DictDao dictRepository;
    private final DictMapper dictMapper;
    private final RedisUtils redisUtils;

    @Override
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
    @Transactional(rollbackFor = Exception.class)
    public void create(Dict resources) {
        dictRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dict resources) {
        // 清理缓存
        delCaches(resources);
        Dict dict = dictRepository.findById(resources.getId()).orElseGet(Dict::new);
        ValidationUtil.isNull( dict.getId(),"Dict","id",resources.getId());
        resources.setId(dict.getId());
        dictRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        // 清理缓存
        List<Dict> dicts = dictRepository.findByIdIn(ids);
        for (Dict dict : dicts) {
            delCaches(dict);
        }
        dictRepository.deleteByIdIn(ids);
    }

    @Override
    public void download(List<DictDTO> dictDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DictDTO dictDTO : dictDtos) {
            if(CollectionUtil.isNotEmpty(dictDTO.getDictDetails())){
                for (DictDetailDTO dictDetail : dictDTO.getDictDetails()) {
                    Map<String,Object> map = new LinkedHashMap<>();
                    map.put("字典名称", dictDTO.getName());
                    map.put("字典描述", dictDTO.getDescription());
                    map.put("字典标签", dictDetail.getLabel());
                    map.put("字典值", dictDetail.getValue());
                    map.put("创建日期", dictDetail.getCreateTime());
                    list.add(map);
                }
            } else {
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("字典名称", dictDTO.getName());
                map.put("字典描述", dictDTO.getDescription());
                map.put("字典标签", null);
                map.put("字典值", null);
                map.put("创建日期", dictDTO.getCreateTime());
                list.add(map);
            }
        }
        FileUtils.downloadExcel(list, response);
    }

    public void delCaches(Dict dict){
        redisUtils.del("dept::name:" + dict.getName());
    }
}
