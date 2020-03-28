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
     * 获取一个时间段的 IP 记录
     *
     * @param date1 startTime
     * @param date2 entTime
     * @return IP数目
     */
    @Query(value = "select count(*) FROM (select request_ip FROM log where create_time between ?1 and ?2 GROUP BY request_ip) as s", nativeQuery = true)
    long findIp(String date1, String date2);

    /**
     * 根据日志类型删除信息
     *
     * @param logType 日志类型
     */
    @Query(nativeQuery = true, value = "delete from log where log_type = ?1")
    @Modifying
    void deleteByLogType(String logType);
}
