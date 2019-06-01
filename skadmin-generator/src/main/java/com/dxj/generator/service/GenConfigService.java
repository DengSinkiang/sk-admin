package com.dxj.generator.service;

import com.dxj.generator.domain.GenConfig;
import com.dxj.generator.repository.GenConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
        return genConfigRepository.save(genConfig);
    }
}
