package com.dxj.dao;

import com.dxj.base.BaseDao;
import com.dxj.domain.entity.GenConfig;

/**
 * @author Sinkiang
 * @date 2019-01-14
 */
public interface GenConfigDao extends BaseDao<GenConfig, Long> {

    /**
     * 查询表配置
     * @param tableName 表名
     * @return /
     */
    GenConfig findByTableName(String tableName);
}
