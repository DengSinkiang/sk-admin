package com.dxj.service;

import com.dxj.domain.entity.Setting;
import org.springframework.stereotype.Service;


/**
 * 配置接口
 *
 * @author Sinkiang
 */
@Service
public interface SettingService {

    /**
     * 通过id获取
     *
     * @param id
     * @return
     */
    Setting get(String id);

    /**
     * 修改
     *
     * @param setting
     * @return
     */
    Setting saveOrUpdate(Setting setting);
}
