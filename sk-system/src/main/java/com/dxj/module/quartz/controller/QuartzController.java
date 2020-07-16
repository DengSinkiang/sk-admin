package com.dxj.module.quartz.controller;

import com.dxj.annotation.Log;
import com.dxj.exception.SkException;
import com.dxj.module.quartz.domain.entity.QuartzJob;
import com.dxj.module.quartz.domain.query.QuartzJobQuery;
import com.dxj.module.quartz.service.QuartzJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author Sinkiang
 * @date 2019-01-07
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs")
@Api(tags = "系统:定时任务管理")
public class QuartzController {

    private static final String ENTITY_NAME = "quartzJob";
    private final QuartzJobService quartzJobService;

    @Log("查询定时任务")
    @ApiOperation("查询定时任务")
    @GetMapping
    @PreAuthorize("@sk.check('timing:list')")
    public ResponseEntity<Object> query(QuartzJobQuery criteria, Pageable pageable) {
        return new ResponseEntity<>(quartzJobService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("导出任务数据")
    @ApiOperation("导出任务数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@sk.check('timing:list')")
    public void download(HttpServletResponse response, QuartzJobQuery criteria) throws IOException {
        quartzJobService.download(quartzJobService.queryAll(criteria), response);
    }

    @Log("导出日志数据")
    @ApiOperation("导出日志数据")
    @GetMapping(value = "/logs/download")
    @PreAuthorize("@sk.check('timing:list')")
    public void downloadLog(HttpServletResponse response, QuartzJobQuery criteria) throws IOException {
        quartzJobService.downloadLog(quartzJobService.queryAllLog(criteria), response);
    }

    @ApiOperation("查询任务执行日志")
    @GetMapping(value = "/logs")
    @PreAuthorize("@sk.check('timing:list')")
    public ResponseEntity<Object> queryJobLog(QuartzJobQuery criteria, Pageable pageable) {
        return new ResponseEntity<>(quartzJobService.queryAllLog(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增定时任务")
    @ApiOperation("新增定时任务")
    @PostMapping
    @PreAuthorize("@sk.check('timing:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody QuartzJob resources) {
        if (resources.getId() != null) {
            throw new SkException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        quartzJobService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改定时任务")
    @ApiOperation("修改定时任务")
    @PutMapping
    @PreAuthorize("@sk.check('timing:edit')")
    public ResponseEntity<Object> update(@Validated(QuartzJob.Update.class) @RequestBody QuartzJob resources) {
        quartzJobService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("更改定时任务状态")
    @ApiOperation("更改定时任务状态")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@sk.check('timing:edit')")
    public ResponseEntity<Object> update(@PathVariable Long id) {
        quartzJobService.updateIsPause(quartzJobService.findById(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("执行定时任务")
    @ApiOperation("执行定时任务")
    @PutMapping(value = "/exec/{id}")
    @PreAuthorize("@sk.check('timing:edit')")
    public ResponseEntity<Object> execution(@PathVariable Long id) {
        quartzJobService.execution(quartzJobService.findById(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除定时任务")
    @ApiOperation("删除定时任务")
    @DeleteMapping
    @PreAuthorize("@sk.check('timing:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        quartzJobService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
