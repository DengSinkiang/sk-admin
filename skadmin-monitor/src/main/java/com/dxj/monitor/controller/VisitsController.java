package com.dxj.monitor.controller;

import com.dxj.monitor.service.VisitsService;
import com.dxj.common.util.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dxj
 * @date 2018-12-13
 */
@RestController
@RequestMapping("api")
public class VisitsController {

    private final VisitsService visitsService;

    @Autowired
    public VisitsController(VisitsService visitsService) {
        this.visitsService = visitsService;
    }

    @PostMapping(value = "/visits")
    public ResponseEntity<Void> create() {
        visitsService.count(RequestHolder.getHttpServletRequest());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/visits")
    public ResponseEntity<Object> get() {
        return new ResponseEntity<>(visitsService.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/visits/chartData")
    public ResponseEntity<Object> getChartData() {
        return new ResponseEntity<>(visitsService.getChartData(), HttpStatus.OK);
    }
}
