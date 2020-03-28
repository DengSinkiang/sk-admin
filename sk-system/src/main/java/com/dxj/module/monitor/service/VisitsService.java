package com.dxj.module.monitor.service;

import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Sinkiang
 * @date 2018-12-13
 */
public interface VisitsService {

    /**
     * 提供给定时任务，每天0点执行
     */
    void save();

    /**
     * 新增记录
     * @param request /
     */
    @Async
    void count(HttpServletRequest request);

    /**
     * 获取数据
     * @return /
     */
    Map<String, Object> get();

    /**
     * getChartData
     * @return /
     */
    Map<String, Object> getChartData();
}
