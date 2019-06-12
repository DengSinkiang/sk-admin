package com.dxj.log.controller;

import com.dxj.common.util.SecurityContextHolder;
import com.dxj.log.domain.Log;
import com.dxj.log.domain.LoginLog;
import com.dxj.log.service.LogService;
import com.dxj.log.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dxj
 * @date 2019-04-24
 */
@RestController
@RequestMapping("api")
public class LogController {

    private final LogService logService;

    private final LoginLogService loginLogService;

    @Autowired
    public LogController(LoginLogService loginLogService, LogService logService) {
        this.loginLogService = loginLogService;
        this.logService = logService;
    }

    @GetMapping(value = "/logs/operation")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Object> getLogs(Log log, Pageable pageable){
        log.setLogType("INFO");
        return new ResponseEntity<>(logService.queryAll(log, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/user")
    public ResponseEntity<Object> getUserLogs(Log log, Pageable pageable){
        log.setLogType("INFO");
        log.setUsername(SecurityContextHolder.getUsername());
        return new ResponseEntity<>(logService.queryAll(log, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/error")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Object> getErrorLogs(Log log, Pageable pageable){
        log.setLogType("ERROR");
        return new ResponseEntity<>(logService.queryAll(log, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/error/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Object> getErrorLogs(@PathVariable Long id){
        return new ResponseEntity<>(logService.findByErrDetail(id), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/login")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Page> getLoginLogs(LoginLog loginLog, Pageable pageable){
        return new ResponseEntity<>(loginLogService.queryAll(loginLog, pageable), HttpStatus.OK);
    }
}
