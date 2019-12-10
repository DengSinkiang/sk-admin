package com.dxj.generator.service;

import com.dxj.generator.entity.GenConfig;
import com.dxj.generator.repository.GenConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

/**
 * @author dxj
 * @date 2019-01-14
 */
@Service
@CacheConfig(cacheNames = "genConfig")
public class GenConfigService {

    private final GenConfigRepository genConfigRepository;

    @Autowired
    public GenConfigService(GenConfigRepository genConfigRepository) {
        this.genConfigRepository = genConfigRepository;
    }

    @Cacheable(key = "'1'")
    public GenConfig find() {
        Optional<GenConfig> genConfig = genConfigRepository.findById(1L);
        return genConfig.orElseGet(GenConfig::new);
    }

    @CacheEvict(allEntries = true)
    public GenConfig update(GenConfig genConfig) {
        genConfig.setId(1L);
        // 自动设置Api路径，注释掉前需要同步取消前端的注释
        String separator = File.separator;
        String[] paths;
        if (separator.equals("\\")) {
            paths = genConfig.getPath().split("\\\\");
        } else {
            paths = genConfig.getPath().split(File.separator);
        }
        StringBuilder api = new StringBuilder();
        for (String path : paths) {
            api.append(path);
            api.append(separator);
            if (path.equals("src")) {
                api.append("api");
                break;
            }
        }
        genConfig.setApiPath(api.toString());
        return genConfigRepository.save(genConfig);
    }
}
