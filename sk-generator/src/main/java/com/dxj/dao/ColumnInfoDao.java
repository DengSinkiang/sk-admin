package com.dxj.dao;


import com.dxj.base.BaseDao;
import com.dxj.domain.entity.ColumnInfo;

import java.util.List;

/**
 * @author Sinkiang
 * @date 2019-01-14
 */
public interface ColumnInfoDao extends BaseDao<ColumnInfo, Long> {

    /**
     * 查询表信息
     * @param tableName 表格名
     * @return 表信息
     */
    List<ColumnInfo> findByTableNameOrderByIdAsc(String tableName);
}
