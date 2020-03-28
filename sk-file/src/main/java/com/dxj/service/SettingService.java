package com.dxj.service;

import com.dxj.dao.SettingDao;
import com.dxj.domain.entity.Setting;
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
public class SettingService  {

    @Autowired
    private SettingDao settingDao;

    @Cacheable(key = "#id")
    public Setting get(String id) {

        return settingDao.findById(id).orElse(new Setting(id));
    }

    @CacheEvict(key = "#setting.id")
    public Setting saveOrUpdate(Setting setting) {

        return settingDao.saveAndFlush(setting);
    }
}
