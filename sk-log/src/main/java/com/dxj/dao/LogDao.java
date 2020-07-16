package com.dxj.dao;

import com.dxj.base.BaseDao;
import com.dxj.domain.entity.Log;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Sinkiang
 * @date 2018-11-24
 */
@Repository
public interface LogDao extends BaseDao<Log, Long> {

    /**
     * 根据日志类型删除信息
     * @param logType 日志类型
     */
    @Modifying
    @Query(value = "delete from sys_log where log_type = ?1", nativeQuery = true)
    void deleteByLogType(String logType);
}
