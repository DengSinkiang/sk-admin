package com.dxj.quartz.task;

import com.dxj.monitor.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dxj
 * @date 2019-01-25
 */
@Component
public class VisitsTask {

    private final VisitService visitService;

    @Autowired
    public VisitsTask(VisitService visitService) {
        this.visitService = visitService;
    }

    public void run() {
        visitService.save();
    }
}
