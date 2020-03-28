package com.dxj.module.quartz.domain.task;

import com.dxj.module.monitor.service.VisitsService;
import org.springframework.stereotype.Component;

/**
 * @author Sinkiang
 * @date 2018-12-25
 */
@Component
public class VisitsTask {

    private final VisitsService visitsService;

    public VisitsTask(VisitsService visitsService) {
        this.visitsService = visitsService;
    }

    public void run(){
        visitsService.save();
    }
}
