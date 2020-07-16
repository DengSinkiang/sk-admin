package com.dxj.module.system.dao;

import com.dxj.base.BaseDao;
import com.dxj.module.system.domain.entity.DictDetail;

import java.util.List;

/**
* @author Sinkiang
* @date 2019-04-10
*/
public interface DictDetailDao extends BaseDao<DictDetail, Long> {

    /**
     * 根据字典名称查询
     * @param name /
     * @return /
     */
    List<DictDetail> findByDictName(String name);
}
