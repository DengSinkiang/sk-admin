package com.dxj.monitor.config;

import com.dxj.monitor.service.VisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * 初始化站点统计
 *
 * @author dxj
 * @date 2019-04-24
 */
@Component
public class VisitInitialization implements ApplicationRunner {

    private final VisitsService visitsService;

    @Autowired
    public VisitInitialization(VisitsService visitsService) {
        this.visitsService = visitsService;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("--------------- 初始化站点统计，如果存在今日统计则跳过 ---------------");
        visitsService.save();
        System.out.println("--------------- 初始化站点统计完成 ---------------");
    }
}
