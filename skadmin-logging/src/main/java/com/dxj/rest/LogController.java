package com.dxj.rest;

import com.dxj.domain.Log;
import com.dxj.domain.LoginLog;
import com.dxj.service.query.LogQueryService;
import com.dxj.service.query.LoginLogQueryService;
import com.dxj.utils.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dxj
 * @date 2018-11-24
 */
@RestController
@RequestMapping("api")
public class LogController {

    @Autowired
    private LogQueryService logQueryService;
    @Autowired
    private LoginLogQueryService loginLogQueryService;

    @GetMapping(value = "/logs")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getLogs(Log log, Pageable pageable){
        log.setLogType("INFO");
        return new ResponseEntity(logQueryService.queryAll(log,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/user")
    public ResponseEntity getUserLogs(Log log, Pageable pageable){
        log.setLogType("INFO");
        log.setUsername(SecurityContextHolder.getUserDetails().getUsername());
        return new ResponseEntity(logQueryService.queryAll(log,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/error")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getErrorLogs(Log log, Pageable pageable){
        log.setLogType("ERROR");
        return new ResponseEntity(logQueryService.queryAll(log,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/login")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getLoginLogs(LoginLog loginLog, Pageable pageable){
        //loginLog.setLogType("INFO");
        return new ResponseEntity(loginLogQueryService.queryAll(loginLog,pageable), HttpStatus.OK);
    }
}
