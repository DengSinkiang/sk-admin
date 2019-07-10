package com.dxj.log.controller;

import cn.hutool.core.lang.Dict;
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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/log/operation")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Object> getLogs(Log logQuery, Pageable pageable){
        logQuery.setLogType("INFO");
        return new ResponseEntity<>(logService.queryAll(logQuery, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/log/user")
    public ResponseEntity<Object> getUserLogs(Log log, Pageable pageable){
        log.setLogType("INFO");
        log.setUsername(SecurityContextHolder.getUsername());
        return new ResponseEntity<>(logService.queryAllByUser(log, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/log/error")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Object> getErrorLogs(Log log, Pageable pageable){
        log.setLogType("ERROR");
        return new ResponseEntity<>(logService.queryAll(log, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/log/error/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Dict> getErrorLogs(@PathVariable Long id){
        return new ResponseEntity<>(logService.findByErrDetail(id), HttpStatus.OK);
    }

    @GetMapping(value = "/log/login")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Page<LoginLog>> getLoginLogs(LoginLog loginLog, @RequestParam(value = "startDate", required=false) String startDate,
                                                       @RequestParam(value = "endDate", required = false) String endDate,
                                                       Pageable pageable){
        return new ResponseEntity<>(loginLogService.queryAll(loginLog, startDate, endDate, pageable), HttpStatus.OK);
    }
}
