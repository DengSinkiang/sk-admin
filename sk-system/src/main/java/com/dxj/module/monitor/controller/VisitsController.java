package com.dxj.module.monitor.controller;

import com.dxj.module.monitor.service.VisitsService;
import com.dxj.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Sinkiang
 * @date 2018-12-13
 */
@RestController
@RequestMapping("/api/visits")
@Api(tags = "系统:访问记录管理")
public class VisitsController {

    private final VisitsService visitsService;

    public VisitsController(VisitsService visitsService) {
        this.visitsService = visitsService;
    }

    @PostMapping
    @ApiOperation("创建访问记录")
    public ResponseEntity<Void> create(){
        visitsService.count(SecurityUtils.getHttpServletRequest());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation("查询")
    public ResponseEntity<Map<String, Object>> get(){
        return new ResponseEntity<>(visitsService.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/chartData")
    @ApiOperation("查询图表数据")
    public ResponseEntity<Map<String, Object>> getChartData(){
        return new ResponseEntity<>(visitsService.getChartData(), HttpStatus.OK);
    }
}
