package com.dxj.module.system.dao;

import com.dxj.base.BaseDao;
import com.dxj.module.system.domain.entity.Job;

import java.util.Set;

/**
* @author Sinkiang
* @date 2019-03-29
*/
public interface JobDao extends BaseDao<Job, Long> {

    /**
     * 根据名称查询
     * @param name 名称
     * @return /
     */
    Job findByName(String name);

    /**
     * 根据Id删除
     * @param ids /
     */
    void deleteAllByIdIn(Set<Long> ids);
}
