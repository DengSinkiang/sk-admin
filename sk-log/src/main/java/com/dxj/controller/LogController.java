package com.dxj.controller;

import com.dxj.annotation.Log;
import com.dxj.domain.dto.LogQuery;
import com.dxj.service.LogService;
import com.dxj.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sinkiang
 * @date 2018-11-24
 */
@RestController
@RequestMapping("/api/log")
@Api(tags = "监控：日志管理")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@sk.check()")
    public void download(HttpServletResponse response, LogQuery criteria) throws IOException {
        criteria.setLogType("INFO");
        logService.download(logService.queryAll(criteria), response);
    }

    @Log("导出错误数据")
    @ApiOperation("导出错误数据")
    @GetMapping(value = "/error/download")
    @PreAuthorize("@sk.check()")
    public void errorDownload(HttpServletResponse response, LogQuery criteria) throws IOException {
        criteria.setLogType("ERROR");
        logService.download(logService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("日志查询")
    @PreAuthorize("@sk.check()")
    public ResponseEntity<Object> getLogs(LogQuery criteria, Pageable pageable) {
        criteria.setLogType("INFO");
        return new ResponseEntity<>(logService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    @ApiOperation("用户日志查询")
    public ResponseEntity<Object> getUserLogs(LogQuery criteria, Pageable pageable) {
        criteria.setLogType("INFO");
        criteria.setBlurry(SecurityUtils.getUsername());
        return new ResponseEntity<>(logService.queryAllByUser(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error")
    @ApiOperation("错误日志查询")
    @PreAuthorize("@sk.check()")
    public ResponseEntity<Object> getErrorLogs(LogQuery criteria, Pageable pageable) {
        criteria.setLogType("ERROR");
        return new ResponseEntity<>(logService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error/{id}")
    @ApiOperation("日志异常详情查询")
    @PreAuthorize("@sk.check()")
    public ResponseEntity<Object> getErrorLogs(@PathVariable Long id) {
        return new ResponseEntity<>(logService.findByErrDetail(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/error")
    @Log("删除所有ERROR日志")
    @ApiOperation("删除所有ERROR日志")
    @PreAuthorize("@sk.check()")
    public ResponseEntity<Object> delAllByError() {
        logService.delAllByError();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/info")
    @Log("删除所有INFO日志")
    @ApiOperation("删除所有INFO日志")
    @PreAuthorize("@sk.check()")
    public ResponseEntity<Void> delAllByInfo() {
        logService.delAllByInfo();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
