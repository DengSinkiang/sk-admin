package com.dxj.tool.service;

import com.dxj.tool.domain.Setting;
import com.dxj.tool.repository.SettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 配置接口
 *
 * @author Sinkiang
 */
@Slf4j
@Service
@Transactional
@CacheConfig(cacheNames = "setting")
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;

    /**
     * 通过id获取
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#id")
    public Setting get(String id) {

        return settingRepository.findById(id).orElse(new Setting(id));
    }

    /**
     * 修改
     *
     * @param setting
     * @return
     */
    @CacheEvict(key = "#setting.id")
    public Setting saveOrUpdate(Setting setting) {

        return settingRepository.saveAndFlush(setting);
    }
}
