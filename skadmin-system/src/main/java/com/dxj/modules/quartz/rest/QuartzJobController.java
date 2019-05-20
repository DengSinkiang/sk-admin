package com.dxj.modules.quartz.rest;

import lombok.extern.slf4j.Slf4j;
import com.dxj.aop.log.Log;
import com.dxj.enums.EntityEnums;
import com.dxj.exception.BadRequestException;
import com.dxj.modules.quartz.domain.QuartzJob;
import com.dxj.modules.quartz.domain.QuartzLog;
import com.dxj.modules.quartz.service.QuartzJobService;
import com.dxj.modules.quartz.service.query.QuartzJobQueryService;
import com.dxj.modules.quartz.service.query.QuartzLogQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author dxj
 * @date 2019-01-07
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class QuartzJobController {

    private final QuartzJobService quartzJobService;

    private final QuartzJobQueryService quartzJobQueryService;

    private final QuartzLogQueryService quartzLogQueryService;

    @Autowired
    public QuartzJobController(QuartzJobService quartzJobService, QuartzJobQueryService quartzJobQueryService, QuartzLogQueryService quartzLogQueryService) {
        this.quartzJobService = quartzJobService;
        this.quartzJobQueryService = quartzJobQueryService;
        this.quartzLogQueryService = quartzLogQueryService;
    }

    @Log("查询定时任务")
    @GetMapping(value = "/jobs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_SELECT')")
    public ResponseEntity<Object> getJobs(QuartzJob resources, Pageable pageable) {
        return new ResponseEntity<>(quartzJobQueryService.queryAll(resources, pageable), HttpStatus.OK);
    }

    /**
     * 查询定时任务日志
     *
     * @param resources
     * @param pageable
     * @return
     */
    @GetMapping(value = "/jobLogs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_SELECT')")
    public ResponseEntity<Object> getJobLogs(QuartzLog resources, Pageable pageable) {
        return new ResponseEntity<>(quartzLogQueryService.queryAll(resources, pageable), HttpStatus.OK);
    }

    @Log("新增定时任务")
    @PostMapping(value = "/jobs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_CREATE')")
    public ResponseEntity<QuartzJob> create(@Validated @RequestBody QuartzJob resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + EntityEnums.QUARTZ_ENTITY + " cannot already have an ID");
        }
        return new ResponseEntity<>(quartzJobService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改定时任务")
    @PutMapping(value = "/jobs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_EDIT')")
    public ResponseEntity<Void> update(@Validated(QuartzJob.Update.class) @RequestBody QuartzJob resources) {
        quartzJobService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("更改定时任务状态")
    @PutMapping(value = "/jobs/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_EDIT')")
    public ResponseEntity<Void> updateIsPause(@PathVariable Long id) {
        quartzJobService.updateIsPause(quartzJobService.findById(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("执行定时任务")
    @PutMapping(value = "/jobs/exec/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_EDIT')")
    public ResponseEntity<Void> execution(@PathVariable Long id) {
        quartzJobService.execution(quartzJobService.findById(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除定时任务")
    @DeleteMapping(value = "/jobs/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        quartzJobService.delete(quartzJobService.findById(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
