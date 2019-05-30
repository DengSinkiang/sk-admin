package com.dxj.task;

import com.dxj.service.VisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dxj
 * @date 2019-01-25
 */
@Component
public class VisitsTask {

    private final VisitsService visitsService;

    @Autowired
    public VisitsTask(VisitsService visitsService) {
        this.visitsService = visitsService;
    }

    public void run() {
        visitsService.save();
    }
}
