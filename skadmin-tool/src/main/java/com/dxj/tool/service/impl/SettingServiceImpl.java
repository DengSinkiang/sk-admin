package com.dxj.tool.service.impl;

import com.dxj.tool.entity.Setting;
import com.dxj.tool.repository.SettingRepository;
import com.dxj.tool.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 配置接口实现
 *
 * @author Sinkiang
 */
@Slf4j
@Service
@Transactional
@CacheConfig(cacheNames = "setting")
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingRepository settingDao;

    @Override
    @Cacheable(key = "#id")
    public Setting get(String id) {

        return settingDao.findById(id).orElse(new Setting(id));
    }

    @Override
    @CacheEvict(key = "#setting.id")
    public Setting saveOrUpdate(Setting setting) {

        return settingDao.saveAndFlush(setting);
    }
}
