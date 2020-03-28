package com.dxj.service.impl;

import com.dxj.dao.GenConfigDao;
import com.dxj.domain.entity.GenConfig;
import com.dxj.service.GenConfigService;
import com.dxj.util.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Sinkiang
 * @date 2019-01-14
 */
@Service
@CacheConfig(cacheNames = "genConfig")
public class GenConfigServiceImpl implements GenConfigService {

    private final GenConfigDao genConfigDao;

    public GenConfigServiceImpl(GenConfigDao genConfigDao) {
        this.genConfigDao = genConfigDao;
    }

    @Override
    @Cacheable(key = "#p0")
    public GenConfig find(String tableName) {
        GenConfig genConfig = genConfigDao.findByTableName(tableName);
        if(genConfig == null){
            return new GenConfig(tableName);
        }
        return genConfig;
    }

    @Override
    @CachePut(key = "#p0")
    public GenConfig update(String tableName, GenConfig genConfig) {
        // 如果 api 路径为空，则自动生成路径
        if(StringUtils.isBlank(genConfig.getApiPath())){
            String separator = File.separator;
            String[] paths;
            String symbol = "\\";
            if (symbol.equals(separator)) {
                paths = genConfig.getPath().split("\\\\");
            } else {
                paths = genConfig.getPath().split(File.separator);
            }
            StringBuilder api = new StringBuilder();
            for (String path : paths) {
                api.append(path);
                api.append(separator);
                if ("src".equals(path)) {
                    api.append("api");
                    break;
                }
            }
            genConfig.setApiPath(api.toString());
        }
        return genConfigDao.save(genConfig);
    }
}
