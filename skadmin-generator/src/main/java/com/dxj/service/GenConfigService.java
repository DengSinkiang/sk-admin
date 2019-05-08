package com.dxj.service;

import com.dxj.domain.GenConfig;
import com.dxj.repository.GenConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
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

    @Autowired
    private GenConfigRepository genConfigRepository;

    @Cacheable(key = "'1'")
    public GenConfig find() {
        Optional<GenConfig> genConfig = genConfigRepository.findById(1L);
        if (genConfig.isPresent()) {
            return genConfig.get();
        } else {
            return new GenConfig();
        }
    }

    @CachePut(key = "'1'")
    public GenConfig update(GenConfig genConfig) {
        genConfig.setId(1L);
        return genConfigRepository.save(genConfig);
    }
}
