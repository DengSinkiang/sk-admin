package com.dxj.module.system.dao;

import com.dxj.base.BaseDao;
import com.dxj.module.system.domain.entity.Dict;

import java.util.List;
import java.util.Set;

/**
* @author Sinkiang
* @date 2019-04-10
*/
public interface DictDao extends BaseDao<Dict, Long> {

    /**
     * 删除
     * @param ids /
     */
    void deleteByIdIn(Set<Long> ids);

    /**
     * 查询
     * @param ids /
     * @return /
     */
    List<Dict> findByIdIn(Set<Long> ids);
}
