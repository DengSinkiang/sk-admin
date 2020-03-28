package com.dxj.module.system.controller;

import com.dxj.annotation.Log;
import com.dxj.config.DataScope;
import com.dxj.exception.SkException;
import com.dxj.module.system.domain.entity.Job;
import com.dxj.module.system.domain.query.JobQuery;
import com.dxj.module.system.service.JobService;
import com.dxj.util.ThrowableUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
* @date 2019-03-29
*/
@Api(tags = "系统：岗位管理")
@RestController
@RequestMapping("/api/job")
public class JobController {

    private final JobService jobService;

    private final DataScope dataScope;

    private static final String ENTITY_NAME = "job";

    public JobController(JobService jobService, DataScope dataScope) {
        this.jobService = jobService;
        this.dataScope = dataScope;
    }

    @Log("导出岗位数据")
    @ApiOperation("导出岗位数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@sk.check('job:list')")
    public void download(HttpServletResponse response, JobQuery criteria) throws IOException {
        jobService.download(jobService.queryAll(criteria), response);
    }

    @Log("查询岗位")
    @ApiOperation("查询岗位")
    @GetMapping
    @PreAuthorize("@sk.check('job:list','user:list')")
    public ResponseEntity<Object> getJobs(JobQuery criteria, Pageable pageable){
        // 数据权限
        criteria.setDeptIds(dataScope.getDeptIds());
        return new ResponseEntity<>(jobService.queryAll(criteria, pageable),HttpStatus.OK);
    }

    @Log("新增岗位")
    @ApiOperation("新增岗位")
    @PostMapping
    @PreAuthorize("@sk.check('job:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Job resources){
        if (resources.getId() != null) {
            throw new SkException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity<>(jobService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改岗位")
    @ApiOperation("修改岗位")
    @PutMapping
    @PreAuthorize("@sk.check('job:edit')")
    public ResponseEntity<Object> update(@Validated(Job.Update.class) @RequestBody Job resources){
        jobService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除岗位")
    @ApiOperation("删除岗位")
    @DeleteMapping
    @PreAuthorize("@sk.check('job:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids){
        try {
            jobService.delete(ids);
        }catch (Throwable e){
            ThrowableUtil.throwForeignKeyException(e, "所选岗位存在用户关联，请取消关联后再试");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
