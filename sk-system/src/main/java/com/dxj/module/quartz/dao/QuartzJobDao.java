package com.dxj.module.quartz.dao;

import com.dxj.base.BaseDao;
import com.dxj.module.quartz.domain.entity.QuartzJob;

import java.util.List;

/**
 * @author Sinkiang
 * @date 2019-01-07
 */
public interface QuartzJobDao extends BaseDao<QuartzJob, Long> {

    /**
     * 查询启用的任务
     *
     * @return List
     */
    List<QuartzJob> findByIsPauseIsFalse();
}
