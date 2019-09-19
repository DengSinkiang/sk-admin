package com.dxj.monitor.controller;

import com.dxj.monitor.service.VisitService;
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
 * @date 2019-04-13
 */
@RestController
@RequestMapping("api")
public class VisitController {

    private final VisitService visitService;

    @Autowired
    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping(value = "/visits")
    public ResponseEntity<Void> create() {
        visitService.count(RequestHolder.getHttpServletRequest());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/visits")
    public ResponseEntity<Object> get() {
        return new ResponseEntity<>(visitService.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/visits/chartData")
    public ResponseEntity<Object> getChartData() {
        return new ResponseEntity<>(visitService.getChartData(), HttpStatus.OK);
    }
}
