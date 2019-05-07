package com.dxj.modules.monitor.rest;

import com.dxj.aop.log.Log;
import com.dxj.modules.monitor.domain.vo.RedisVo;
import com.dxj.modules.monitor.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author dxj
 * @date 2018-12-10
 */
@RestController
@RequestMapping("api")
public class RedisController {

    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @Log("查询Redis缓存")
    @GetMapping(value = "/redis")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_SELECT')")
    public ResponseEntity<Page> getRedis(String key, Pageable pageable) {
        return new ResponseEntity<>(redisService.findByKey(key, pageable), HttpStatus.OK);
    }

    @Log("新增Redis缓存")
    @PostMapping(value = "/redis")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_CREATE')")
    public ResponseEntity<Void> create(@Validated @RequestBody RedisVo resources) {
        redisService.save(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改Redis缓存")
    @PutMapping(value = "/redis")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_EDIT')")
    public ResponseEntity<Void> update(@Validated @RequestBody RedisVo resources) {
        redisService.save(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除Redis缓存")
    @DeleteMapping(value = "/redis")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_DELETE')")
    public ResponseEntity<Void> delete(@RequestBody RedisVo resources) {
        redisService.delete(resources.getKey());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Log("清空Redis缓存")
    @DeleteMapping(value = "/redis/all")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_DELETE')")
    public ResponseEntity<Void> deleteAll() {
        redisService.flushdb();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
