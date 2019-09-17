package com.dxj.monitor.controller;

import com.dxj.monitor.domain.ServerBaseEntity;
import com.dxj.monitor.domain.ServerInstantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 */
@RestController
@RequestMapping("api/monitor/server")
public class MonitorServerController {

    @Autowired
    private ServerBaseEntity base;

    @Autowired
    private ServerInstantEntity instant;

    @GetMapping("/base")
    public ResponseEntity<ServerBaseEntity> baseInfo() {
        base.init();
        return new ResponseEntity<>(base, HttpStatus.OK);
    }

    @GetMapping("/instant")
    public ResponseEntity<ServerInstantEntity> instantInfo() {
        instant.init();
        return new ResponseEntity<>(instant, HttpStatus.OK);
    }
}
