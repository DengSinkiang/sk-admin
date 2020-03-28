package com.dxj.module.quartz.config;

import com.dxj.module.quartz.dao.QuartzJobDao;
import com.dxj.module.quartz.domain.entity.QuartzJob;
import com.dxj.module.quartz.util.QuartzManage;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sinkiang
 * @date 2019-01-07
 */
@Component
public class JobRunner implements ApplicationRunner {

    private final QuartzJobDao quartzJobDao;

    private final QuartzManage quartzManage;

    public JobRunner(QuartzJobDao quartzJobDao, QuartzManage quartzManage) {
        this.quartzJobDao = quartzJobDao;
        this.quartzManage = quartzManage;
    }

    /**
     * 项目启动时重新激活启用的定时任务
     * @param applicationArguments /
     */
    @Override
    public void run(ApplicationArguments applicationArguments){
        System.out.println("-------------------- 开始注入定时任务 ---------------------");
        List<QuartzJob> quartzJobs = quartzJobDao.findByIsPauseIsFalse();
        quartzJobs.forEach(quartzManage::addJob);
        System.out.println("-------------------- 定时任务注入完成 ---------------------");
    }
}
